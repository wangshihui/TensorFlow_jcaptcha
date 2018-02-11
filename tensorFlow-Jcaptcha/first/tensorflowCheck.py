'''
Created on 2018年1月2日

@author: wangshihui
'''
import tensorflow as tf



# d=tf.random_uniform();

# print(d)
# 常量
hello = tf.constant('Hello, TensorFlow!')
sess = tf.Session()


print(sess.run(hello))

# 占位符
a = tf.placeholder(tf.float32)
b = tf.placeholder(tf.float32)

adder_node = a + b  # + provides a shortcut for tf.add(a, b)

# 传播性
add_and_triple = adder_node * 3.

print(sess.run(adder_node, {a: 3, b: 4.5}))
print(sess.run(adder_node, {a: [1, 3], b: [2, 4]}))
print(sess.run(add_and_triple, {a: 3, b: 4.5}))

# 变量
W = tf.Variable([.3], dtype=tf.float32)
c = tf.Variable([-.3], dtype=tf.float32)
x = tf.placeholder(tf.float32)
linear_model = W*x + c
init = tf.global_variables_initializer()
sess.run(init)
print(sess.run(linear_model, {x: [1, 2, 3, 4]}))
print(W)
print(c)
sess.close()