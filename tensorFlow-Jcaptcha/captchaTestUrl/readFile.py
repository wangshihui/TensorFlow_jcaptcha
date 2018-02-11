'''
Created on 2018年1月10日

@author: wangshihui
'''
import urllib.request
from PIL import Image

codeGenUrl="http://localhost:8080/captcha?code=1234";

try:  
    img=urllib.request.urlopen(codeGenUrl)
    
    img=Image.open(img)
    img=img.convert('L')
    img.show();
except:  
    print("sth error")
