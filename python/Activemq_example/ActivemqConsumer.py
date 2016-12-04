import stomp
import time
import ActivemqListener
import threading

def listen():

    connection = stomp.Connection(host_and_ports=[('localhost',61613)])

    connection.set_listener('ActivemqListener',ActivemqListener.Listener(connection))

    connection.start()

    connection.connect()

    connection.subscribe('/topic/ExampleTopic',1)

    while True:
        time.sleep(1)

    connection.disconnect()

t = threading.Thread(target=listen)
t.start()
