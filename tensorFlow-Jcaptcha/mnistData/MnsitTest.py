'''
Created on 2018年1月8日

@author: wangshihui
'''
import tensorflow as tf
import input_data
from _ctypes import sizeof
sess = tf.InteractiveSession()
'''
    shape=[None, 784]
        784代表一个图片的784个特征（像素）
            x表示任意多个手写图片
    shape=[None, 10]
        10代表10个输出值
        每一行代表
'''
x = tf.placeholder("float", shape=[None, 784])

#正确的结果
y_ = tf.placeholder("float", shape=[None, 10])

'''
#　W可以理解为　784个像素的   权重     的10维数组 可以理解为， 0,1,2,3,4,5,6,7,8,9分别代表的像素权重
#　MNSIT输入可以理解为一个784个像素的 5000+维度的数组
'''
W=tf.Variable(tf.zeros([784,10]))
b=tf.Variable(tf.zeros([10]))
sess.run(tf.initialize_all_variables())


# 预测额结果
y = tf.nn.softmax(tf.matmul(x,W) + b)
# 交叉熵
cross_entropy = -tf.reduce_sum(y_*tf.log(y))
'''
# train_step 就是训练模型
'''
train_step = tf.train.GradientDescentOptimizer(0.01).minimize(cross_entropy)
mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)

for i in range(1000):
    batch = mnist.train.next_batch(50)
        
    ''' 
        x 为输入  shape=[50, 784]
        y_为输出 shape=[50, 10]
                    以此来训练参数 W和b
    '''
    train_step.run(feed_dict={x: batch[0], y_: batch[1]})
    
correct_prediction = tf.equal(tf.argmax(y,1), tf.argmax(y_,1))
accuracy = tf.reduce_mean(tf.cast(correct_prediction, "float"))

print(accuracy.eval(feed_dict={x: mnist.test.images, y_: mnist.test.labels}))
