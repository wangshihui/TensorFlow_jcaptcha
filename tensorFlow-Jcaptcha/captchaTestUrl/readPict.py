'''
Created on 2018年1月10日

@author: wangshihui
'''
from captcha.image import ImageCaptcha
from PIL import Image
import numpy as np
import random
import string
import os
from urllib import request

class readPict():
    def __init__(self,
                 width = 250,#验证码图片的宽
                 height = 100,#验证码图片的高
                 char_num = 4,#验证码字符个数
#                  characters = string.digits + string.ascii_uppercase + string.ascii_lowercase):#验证码组成，数字+大写字母+小写字母
                characters = string.ascii_uppercase + string.ascii_lowercase):#验证码组成，数字+大写字母+小写字母
        self.width = width
        self.height = height
        self.char_num = char_num
        self.characters = characters
        self.classes = len(characters)
        self.files=[]
        self.batch=1;
        self.codeGenUrl="http://localhost:8080/captcha?code="
#         self.codeGenUrl="http://localhost:8081/checkCode?code="
        
    def readPicture(self,batch_size):
#         print("start from here",self.batch)
        # X  用来保存产生的图像
        X = np.zeros([batch_size,self.height,self.width,1])
        img = np.zeros((self.height,self.width),dtype=np.uint8)
        #　Y　图像label　三维数组　一维表示图像　二维　三维表示图像中的字符
        Y = np.zeros([batch_size,self.char_num,self.classes])
        while True: 
            for i in range(batch_size):
                captcha_str = ''.join(random.sample(self.characters,self.char_num))
                try:
                    img = Image.open(request.urlopen(self.codeGenUrl+captcha_str))
                except:  
                    continue
                
                img=img.convert('L')
                img = np.array(img.getdata())
                X[i] = np.reshape(img,[self.height,self.width,1])/255.0
                for j,ch in enumerate(captcha_str):
                    Y[i,j,self.characters.find(ch)] = 1
                    
            Y = np.reshape(Y,(batch_size,self.char_num*self.classes))
            self.batch=self.batch+1
            yield X,Y
        '''
        while True:  
            for i in range(batch_size):
                # 生成的图像中的字符
                captcha_str = ''.join(random.sample(self.characters,self.char_num))
                # 生成图像 同时转成灰度图像
                img = image.generate_image(captcha_str).convert('L')
                #　图像转成数组
                img = np.array(img.getdata())
                # 将图像变形成，只有一个数据的 三维数组
                X[i] = np.reshape(img,[self.height,self.width,1])/255.0
                # 将标签构成一个三维数组
                for j,ch in enumerate(captcha_str):
                    Y[i,j,self.characters.find(ch)] = 1
            Y = np.reshape(Y,(batch_size,self.char_num*self.classes))
            yield X,Y
        '''
    def get_parameter(self):
        return self.width,self.height,self.char_num,self.characters,self.classes
    
    def randomReadPicture(self,batch_size):
#         print("start from here",self.batch)
        # X  用来保存产生的图像
        X = np.zeros([batch_size,self.height,self.width,1])
        img = np.zeros((self.height,self.width),dtype=np.uint8)
        #　Y　图像label　三维数组　一维表示图像　二维　三维表示图像中的字符
        Y = np.zeros([batch_size,self.char_num,self.classes])
        
        for i in range(batch_size):
            captcha_str = ''.join(random.sample(self.characters,self.char_num))
            try:
                img = Image.open(request.urlopen(self.codeGenUrl+captcha_str))
            except:  
                continue
            img=img.convert('L')
            img = np.array(img.getdata())
            X[i] = np.reshape(img,[self.height,self.width,1])/255.0
            for j,ch in enumerate(captcha_str):
                Y[i,j,self.characters.find(ch)] = 1
                
        Y = np.reshape(Y,(batch_size,self.char_num*self.classes))
        yield X,Y