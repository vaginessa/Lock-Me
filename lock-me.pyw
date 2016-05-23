import SimpleHTTPServer
import SocketServer
import logging
import cgi
import sys
import ctypes

PORT = 65535
SECRET = "secret_pass"

if(len(sys.argv)!=3):
    print("Usage: %s <port> <secret>"%(sys.argv[0]))
    print "[*] Using default configuration."
else:
    PORT = int(sys.argv[1])
    SECRET = sys.argv[2]

class ServerHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):

    def do_GET(self):
        logging.error(self.headers)
        #SimpleHTTPServer.SimpleHTTPRequestHandler.do_GET(self)

    def do_POST(self):
        logging.error(self.headers)
        form = cgi.FieldStorage(
            fp=self.rfile,
            headers=self.headers,
            environ={'REQUEST_METHOD':'POST',
                     'CONTENT_TYPE':self.headers['Content-Type'],
                     })
        for key in form:
            print '%s: %s' % (key, form[key].value)
            if(key=="secret"):
                if(form[key].value==SECRET):
                    print "You are authorized"
                    ctypes.windll.user32.LockWorkStation()
                    break;
        #SimpleHTTPServer.SimpleHTTPRequestHandler.do_GET(self)

Handler = ServerHandler

httpd = SocketServer.TCPServer(("0.0.0.0", PORT), Handler)

print "serving at port", PORT
httpd.serve_forever()