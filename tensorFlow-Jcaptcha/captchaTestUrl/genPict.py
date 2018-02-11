'''
Created on 2018年1月2日

@author: wangshihui
'''
import generate_captcha

g = generate_captcha.generateCaptcha()
g.gen_captcha(64)
# g.gen_test_captcha()