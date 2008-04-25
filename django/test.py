import pyexpat

from mod_python import apache

def handler(req):
    req.content_type = 'text/plain'
    req.send_http_header()
    req.write('with pyexpat\n')
    return apache.OK
