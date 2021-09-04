import json

import requests


url= "http://httpbin.org/ip"

r = requests.get(url)

print(r.text)

ip = json.loads(r.text)["origin"]

print("我的外网ip", ip)