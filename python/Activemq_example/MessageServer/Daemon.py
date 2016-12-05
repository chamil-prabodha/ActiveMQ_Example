class Daemon():
    def __init__(self,host,port):
        self.__host = host
        self.__port = port

    def setup_connection(self,listener_name,listener):
        raise NotImplementedError("Subclass must impplement abstract method")

    def start(self):
        raise NotImplementedError("Subclass must impplement abstract method")

    def subscribe(self,topic,arg):
        raise NotImplementedError("Subclass must impplement abstract method")

    def disconnect(self):
        raise NotImplementedError("Subclass must impplement abstract method")