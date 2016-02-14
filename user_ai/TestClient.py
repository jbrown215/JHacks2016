from twisted.internet.protocol import Protocol, ClientFactory
from twisted.internet import reactor
import json
import random

CURRENT_ORDER_ID = 0
COMPANIES = ["GOOG","MOOG","FOOD","THROOG","BOOG"]

class Echo(Protocol):

    def buy(self, symbol, price, amount):
        global CURRENT_ORDER_ID
        print("Getting ready to buy!")
        message = {"action":"buy", "security":symbol, "price":price, "quantity":amount, "orderNumber":CURRENT_ORDER_ID} 
        encodedMessage = json.dumps(message)
        self.transport.write(encodedMessage + "\n")
        CURRENT_ORDER_ID += 1

    def sell(self, symbol, price, amount):
        global CURRENT_ORDER_ID
        print("Getting ready to sell!")
        message = {"action":"sell", "security":symbol, "price":price, "quantity":amount, "orderNumber":CURRENT_ORDER_ID} 
        encodedMessage = json.dumps(message)
        self.transport.write(encodedMessage + "\n")
        CURRENT_ORDER_ID += 1

    #Runs every time a message is sent out from the server
    def dataReceived(self, data):
	global COMPANIES
        print("got stuff!")
        try:
            for line in data.split("\n"):
                if line:
                    #Take input of market and convert to readable python format
                    #json reads it in and turns it into a python dictionary
                    print(data)
                    line = json.loads(line.strip())
                    print("worked the data!")
		    comp1 = COMPANIES[random.randint(len(COMPANIES)-1)]
		    comp2 = COMPANIES[random.randint(len(COMPANIES)-1)]
                    self.buy(comp1, random.randint(25, 75), 23)
                    self.sell(comp2, random.randint(25, 75), 10)
        except ValueError:
            print "nah"

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
        self.buy("GOOG", random.randint(50, 100), 10)

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
