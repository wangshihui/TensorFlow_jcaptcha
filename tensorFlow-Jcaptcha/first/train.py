def createGenerator():
    mylist = range(3)
    while True:
        for i in mylist:
            yield i*i
mygenerator = createGenerator() # create a generator
print(mygenerator) # mygenerator is an object!
for i in mygenerator:
    print(i)