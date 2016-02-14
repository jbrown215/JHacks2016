# JHacks2016
JHacks 2016 repo

TODO: Format this file
buy 
sell
cancel
unpack
connect
pack.


CLIENT MESSAGES:

BUY:
{ action : buy, security : name, price : $$, orderNumber : id }, returns {subject: buy confirmation, status : success/fail,  order number}

SELL:
{action : sell, security : name, price : $$, orderNumber : id}, returns {subject : sell confirmation ,status : success/fail, order number}

CONNECT:
{action : connect, name}, returns {subject: connect confirmation, status : success/fail}

CANCEL:
{action : cancel, orderNumber : id}, returns {subject: cancel confirmation, success/fail, id}

UNPACK:
{action : unpack, ETF, orderId} returns {subject: unpack confirmation, success/fail, id}

PACK:
{action : pack, ETF, orderId} returns {subject : pack confirmation, success/fail, id}

SERVER MESSAGES:

state
trade
x confirmation


STATE
{subject : state, marketInfo : [{stock, [top 5 bids], [top 5 asks]}…], score}
{subject : trade, security: name, price : $$}


TEAMS:

GUI (server side data + client side setup)
Driving Agents/Testing agents
Server



Requirements from user: 
runnable bot
./run.sh script 
adi
words
Ayelet
Jon
