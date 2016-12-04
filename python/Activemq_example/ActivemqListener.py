import stomp

class Listener(stomp.ConnectionListener):

    def __init__(self,conn):
        self.__conn = conn

    def on_message(self,headers,message):
        self.__conn.send('/topic/ReceiveTopic',message)
        print(message)