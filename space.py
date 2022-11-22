import requests
import json
import webbrowser

API_KEY=
url='https://api.nasa.gov/planetary/apod'
params={
'api_key':API_KEY,
'hd': 'True',
'date' :'2011-11-8'
}
response=requests.get(url,params=params)
json_data=json.loads(response.text)
#print(json_data)
image_url=json_data('hdurl')
webbrowser.open(image_url)
