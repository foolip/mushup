// HTMLParser Library - A java-based parser for HTML
// http://htmlparser.org
// Copyright (C) 2006 Derrick Oswald
//
// Revision Control Information
//
// $URL: https://htmlparser.svn.sourceforge.net/svnroot/htmlparser/trunk/parser/src/main/java/org/htmlparser/filters/HasAttributeFilter.java $
// $Author: derrickoswald $
// $Date: 2006-09-16 16:44:17 +0200 (Sat, 16 Sep 2006) $
// $Revision: 4 $
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the Common Public License; either
// version 1.0 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// Common Public License for more details.
//
// You should have received a copy of the Common Public License
// along with this library; if not, the license is available from
// the Open Source Initiative (OSI) website:
//   http://opensource.org/licenses/cpl1.0.php

package org.foolip.mushup;

import java.util.Locale;

import org.htmlparser.Attribute;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Tag;

/**
 * This class accepts all tags that have a certain attribute,
 * and optionally, with a certain value.
 */
public class CssClassFilter implements NodeFilter
{
    /**
     * The class to check for.
     */
    protected String mCssClass;

    /**
     * Creates a new instance of CssClassFilter that accepts tags
     * with the given css class.
     * @param cssClass The class to search for.
     */
    public CssClassFilter (String cssClass)
    {
        mCssClass = cssClass.toUpperCase(Locale.ENGLISH);
    }

    /**
     * Accept tags with the given CSS class.
     * @param node The node to check.
     * @return <code>true</code> if the node matches the CSS class,
     * <code>false</code> otherwise.
     */
    public boolean accept (Node node)
    {
        if (node instanceof Tag) {
	    Attribute attribute = ((Tag)node).getAttributeEx("class");
	    if (attribute != null) {
		String[] classes = attribute.getValue().split("\\s");
		for (int i=0; i<classes.length; i++) {
		    if (mCssClass.equals(classes[i].toUpperCase(Locale.ENGLISH))) {
			return true;
		    }
		}
	    }
        }
        return false;
    }
}
