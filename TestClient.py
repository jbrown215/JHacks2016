from twisted.internet.protocol import Protocol, ClientFactory
from twisted.internet import reactor
import json

CURRENT_ORDER_ID = 0

class Echo(Protocol):

    def buy(self, symbol, price, amount):
        global CURRENT_ORDER_ID
        print("Getting ready to send a message")
        message = {"action":"buy", "security":symbol, "price":price, "quantity":amount, "orderNumber":CURRENT_ORDER_ID} 
        encodedMessage = json.dumps(message)
        self.transport.write(encodedMessage + "\n")
        print("sent a message!")
        CURRENT_ORDER_ID += 1

    def sell(self, symbol, price, amount):
        global CURRENT_ORDER_ID
        self.transport.write(order.toJSON()+ "\n")
        CURRENT_ORDER_ID += 1

    #Runs every time a message is sent out from the server
    def dataReceived(self, data):
        print("got stuff!")
        #Take input of market and convert to readable python format
        for count in range(2,len(data)-3):
            if data[count] == "{":
                data = data[:count] + "[" + data[count+1:]
            elif data[count] == "}":
                data = data[:count] + "]" + data[count+1:]
        #json reads it in and turns it into a python dictionary
        print(data)
        data = json.loads(data)
        print("worked the data!")
        if (data["subject"] == "state"):
            return self.workData(data)
        else:
            print ("not in state mode")

    #This function decides what the bot would want to buy
    #The bot just wants to buy 5 of the lowest priced stock
    def workData(self,data):
        name = ""
        price = None
        for company in data["marketInfo"]:
            if (price == None or company[2][-1] < price):
                name = company[0]
                price = company[2][-1]
        print("about to buy!")
        return self.buy(name,price,5)

    def connectionMade(self):
        print (":)")

class EchoClientFactory(ClientFactory):

    def startedConnecting(self, connector):
        print 'Started to connect.'

    def buildProtocol(self, addr):
        print 'Connected.'
        return Echo()

    def clientConnectionLost(self, connector, reason):
        print("Connection Lost")
        reactor.stop()

    def clientConnectionFailed(self, connector, reason):
        print 'Connection failed. Reason:', reason

host = "127.0.0.1"
port = 15213
reactor.connectTCP(host, port, EchoClientFactory())
reactor.run()
