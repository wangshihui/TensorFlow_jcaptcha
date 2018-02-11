'''
Created on 2018年1月10日

@author: wangshihui
'''
import os
from PIL import Image, ImageFilter
for root, dirs, files in os.walk("D:\imgout", topdown=False):
    for name in files:
        os.path.join(root, name)
        gray_image = Image.open(os.path.join(root, name)).convert('L')
        print(gray_image)
