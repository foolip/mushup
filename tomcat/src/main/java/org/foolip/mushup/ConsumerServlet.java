package org.foolip.mushup;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.OpenIDException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.ParameterList;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;

/**
 * Based on OpenID4Java Consumer Servlet sample from 0.9.4.339
 * Original author Sutra Zhou
 */
public class ConsumerServlet extends javax.servlet.http.HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5998885243419513055L;

	private final Log log = LogFactory.getLog(this.getClass());

	private ServletContext context;

	private ConsumerManager manager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		context = config.getServletContext();

		log.debug("context: " + context);

		try {
			this.manager = new ConsumerManager();
		} catch (ConsumerException e) {
			throw new ServletException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (req.getParameterMap().isEmpty()) {
			// begin login process
			this.getServletContext().getRequestDispatcher("/login.jsp")
					.forward(req, resp);
		} else {
			this.verifyResponse(req, resp);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String identifier = req.getParameter("identifier");
		this.authRequest(identifier, req, resp);
	}

	// --- placing the authentication request ---
	public void authRequest(String userSuppliedString,
			HttpServletRequest httpReq, HttpServletResponse httpResp)
	                throws ServletException, IOException {
		try {
			// configure the return_to URL where your application will receive
			// the authentication responses from the OpenID provider
			String returnToUrl = httpReq.getRequestURL().toString();

			// --- Forward proxy setup (only if needed) ---
			// ProxyProperties proxyProps = new ProxyProperties();
			// proxyProps.setProxyName("proxy.example.com");
			// proxyProps.setProxyPort(8080);
			// HttpClientFactory.setProxyProperties(proxyProps);

			// perform discovery on the user-supplied identifier
			List discoveries = manager.discover(userSuppliedString);

			// attempt to associate with the OpenID provider
			// and retrieve one service endpoint for authentication
			DiscoveryInformation discovered = manager.associate(discoveries);

			// store the discovery information in the user's session
			httpReq.getSession().setAttribute("openid-disc", discovered);

			// obtain a AuthRequest message to be sent to the OpenID provider
			AuthRequest authReq = manager.authenticate(discovered, returnToUrl);

			// Attribute Exchange example: fetching the 'email' attribute
			FetchRequest fetch = FetchRequest.createFetchRequest();

			if ("1".equals(httpReq.getParameter("nickname"))) {
				fetch.addAttribute("nickname",
				// attribute alias
						"http://schema.openid.net/contact/nickname", // type
						// URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("email"))) {
				fetch.addAttribute("email",
				// attribute alias
						"http://schema.openid.net/contact/email", // type URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("fullname"))) {
				fetch.addAttribute("fullname",
				// attribute alias
						"http://schema.openid.net/contact/fullname", // type
						// URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("dob"))) {
				fetch.addAttribute("dob",
				// attribute alias
						"http://schema.openid.net/contact/dob", // type URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("gender"))) {
				fetch.addAttribute("gender",
				// attribute alias
						"http://schema.openid.net/contact/gender", // type URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("postcode"))) {
				fetch.addAttribute("postcode",
				// attribute alias
						"http://schema.openid.net/contact/postcode", // type
						// URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("country"))) {
				fetch.addAttribute("country",
				// attribute alias
						"http://schema.openid.net/contact/country", // type URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("language"))) {
				fetch.addAttribute("language",
				// attribute alias
						"http://schema.openid.net/contact/language", // type
						// URI
						true); // required
			}
			if ("1".equals(httpReq.getParameter("timezone"))) {
				fetch.addAttribute("timezone",
				// attribute alias
						"http://schema.openid.net/contact/timezone", // type
						// URI
						true); // required
			}

			// attach the extension to the authentication request
			authReq.addExtension(fetch);

			if (!discovered.isVersion2()) {
				// Option 1: GET HTTP-redirect to the OpenID Provider endpoint
				// The only method supported in OpenID 1.x
				// redirect-URL usually limited ~2048 bytes
				httpResp.sendRedirect(authReq.getDestinationUrl(true));
			} else {
				// Option 2: HTML FORM Redirection (Allows payloads >2048 bytes)

                                httpReq.setAttribute("parameterMap", httpReq.getParameterMap());
                                httpReq.setAttribute("message", authReq);
                                this.getServletContext().getRequestDispatcher("/formredirection.jsp")
				    .forward(httpReq, httpResp);
			}
		} catch (OpenIDException e) {
			httpReq.setAttribute("error", e.getMessage());
			this.getServletContext().getRequestDispatcher("/login.jsp")
			    .forward(httpReq, httpResp);
		}
	}

	// --- processing the authentication response ---
	public void verifyResponse(HttpServletRequest httpReq, HttpServletResponse httpResp)
	                throws ServletException, IOException {
		log.debug("------------------------");
		log.debug("context: " + context);
		try {
			// extract the parameters from the authentication response
			// (which comes in as a HTTP request from the OpenID provider)
			ParameterList response = new ParameterList(httpReq
					.getParameterMap());

			// retrieve the previously stored discovery information
			DiscoveryInformation discovered = (DiscoveryInformation) httpReq
					.getSession().getAttribute("openid-disc");

			// extract the receiving URL from the HTTP request
			StringBuffer receivingURL = httpReq.getRequestURL();
			String queryString = httpReq.getQueryString();
			if (queryString != null && queryString.length() > 0)
				receivingURL.append("?").append(httpReq.getQueryString());

			// verify the response; ConsumerManager needs to be the same
			// (static) instance used to place the authentication request
			VerificationResult verification = manager.verify(receivingURL
					.toString(), response, discovered);

			// examine the verification result and extract the verified
			// identifier
			Identifier verified = verification.getVerifiedId();
			if (verified != null) {
				AuthSuccess authSuccess = (AuthSuccess) verification
						.getAuthResponse();

				if (authSuccess.hasExtension(AxMessage.OPENID_NS_AX)) {
					FetchResponse fetchResp = (FetchResponse) authSuccess
							.getExtension(AxMessage.OPENID_NS_AX);

					// List emails = fetchResp.getAttributeValues("email");
					// String email = (String) emails.get(0);

					List aliases = fetchResp.getAttributeAliases();
					for (Iterator iter = aliases.iterator(); iter.hasNext();) {
						String alias = (String) iter.next();
						List values = fetchResp.getAttributeValues(alias);
						if (values.size() > 0) {
							log.debug(alias + " : " + values.get(0));
							httpReq.setAttribute(alias, values.get(0));
						}
					}
				}

				log.debug("identifier: " + verified);
				// FIXME: add user to neobase
				httpReq.getSession().setAttribute("openid", verified.getIdentifier());
				httpReq.setAttribute("identifier", verified.getIdentifier());
				//this.getServletContext().getRequestDispatcher("/return.jsp")
				//	.forward(httpReq, httpResp);
				httpResp.sendRedirect(""); // application root
			} else {
				// This seems to happen when the user cancels
				this.getServletContext().getRequestDispatcher("/login.jsp")
					.forward(httpReq, httpResp);
			}
		} catch (OpenIDException e) {
			httpReq.setAttribute("error", e.getMessage());
			this.getServletContext().getRequestDispatcher("/login.jsp")
			    .forward(httpReq, httpResp);
		}
	}
}
