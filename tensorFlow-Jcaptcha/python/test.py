#!/usr/bin/python

# -*- coding: utf-8 -*-

"""

-------------------------------------------------------------------------------

Function:

【整理】Python中：self和init__的含义与使用

  

Verison:    2017-10-10

-------------------------------------------------------------------------------

"""

  

#注：此处全局的变量名，写成name，只是为了演示而用

#实际上，好的编程风格，应该写成gName之类的名字，以表示该变量是Global的变量

name ="whole global name";

  

class Person:
    name ="11"
    def __init__(self, newPersonName):

        self.name = newPersonName;

          

        #1.如果此处不写成self.name

        #那么此处的name，只是__init__函数中的局部临时变量name而已

        #和全局中的name，没有半毛钱关系

        name =newPersonName;#此处的name，只是__init__函数中的局部临时变量name而已

          

        #此处只是为了代码演示，而使用了局部变量name，

        #不过需要注意的是，此处很明显，由于接下来的代码也没有利用到此处的局部变量name

        #则会导致：此处的name变量，实际上被浪费了，根本没有被使用

  

    def sayYourName(self):

        #此处由于找不到实例中的name变量，所以会报错：

        #AttributeError: Person instance has no attribute 'name'

        print('My name is %s'%(self.name));

  

def selfAndInitDemo():

    personInstance =Person("crifan");

    personInstance.sayYourName();

      

###############################################################################

if __name__=="__main__":

    selfAndInitDemo();