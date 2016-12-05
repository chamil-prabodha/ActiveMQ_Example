import Daemon
import stomp

class ActivemqDaemon(Daemon.Daemon,object):

    class __ActivemqDaemon():
        def __init__(self,host,port):
            self.__host = host
            self.__port = port
            self.__connection = None

        def __str__(self):
            return repr(self)+self.__host

    instance = None

    def __init__(self,host,port):
        if ActivemqDaemon.instance is None:
            ActivemqDaemon.instance = ActivemqDaemon.__ActivemqDaemon(host,port)
            super(ActivemqDaemon, self).__init__(host,port)
            self.instance.__host = host
            self.instance.__port = port
            self.instance.__connection = None

    def setup_connection(self,listener_name,listener):
        self.instance.__connection = stomp.Connection(host_and_ports=[(self.instance.__host,self.instance.__port)])
        self.instance.__connection.set_listener(listener_name,listener(self.instance.__connection))

    def subscribe(self,topic,arg):
        self.instance.__connection.subscribe(topic,arg)

    def start(self):
        self.instance.__connection.start()
        self.instance.__connection.connect()
        print('Activemq python stomp server running on :' + str(self.instance.__port))

    def disconnect(self):
        self.instance.__connection.disconnect()

    def get_connection(self):
        return self.instance.__connection

    def __getattr__(self, name):
        return getattr(self.instance, name)