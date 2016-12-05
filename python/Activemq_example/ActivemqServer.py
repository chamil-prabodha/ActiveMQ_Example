import stomp
import time
import ActivemqListener
import threading
from MessageServer import ActivemqDaemon

def listen():

    daemon = ActivemqDaemon.ActivemqDaemon('localhost',61613)
    daemon.setup_connection('ActiveListener',ActivemqListener.Listener)
    daemon.start()
    daemon.subscribe('/topic/ExampleTopic',1)

    while True:
        time.sleep(1)

    daemon.disconnect()

t = threading.Thread(target=listen)
t.start()
