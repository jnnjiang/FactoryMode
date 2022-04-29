### Intent parameter introduce
- imagePath :absolutely path
- showButton : defalut false. if true will show button on page,the image will show while user click the button
- compress : default true.compress image to avoid memory overflow caused by too large images

### file Path
/sdcard/FactoryImage/

### start Activity on CID:
```
adb shell am start -n com.ff.factoryimage/.MainActivity --es imagePath "/sdcard/FactoryImage/imagetest.jpg"
```
IF need show button,do:
```
adb shell am start -n com.ff.factoryimage/.MainActivity --es imagePath "/sdcard/FactoryImage/imagetest.jpg" --ez showButton true
```
IF don't need compress image
```
adb shell am start -n com.ff.factoryimage/.MainActivity --es imagePath "/sdcard/FactoryImage/imagetest.jpg" --ez compress false
```
### start Activity on IPD:

```
adb shell am start --display 2 -n com.ff.factoryimage/.MainActivity --es imagePath "/sdcard/FactoryImage/imagetest.jpg"
```
IF need show button,do:
```
adb shell am start --display 2 -n com.ff.factoryimage/.MainActivity --es imagePath "/sdcard/FactoryImage/imagetest.jpg" --ez showButton true
```
IF don't need compress image
```
adb shell am start --display 2 -n com.ff.factoryimage/.MainActivity --es imagePath "/sdcard/FactoryImage/imagetest.jpg" --ez compress false
```

### Stop Activity
```
adb shell input keyevent KEYCODE_BACK
```

### Stop app
```
adb shell am force-stop com.ff.factoryimage
```

<b> for not root device </b>
```
adb shell ps => Will list all running processes on the device and their process ids 
adb shell kill <PID> => Instead of <PID> use process id of your application
```

### Steps
1.create a directory factoryImage 

2.push images to /sdcard/factoryImage/ 

3.execute start actvity command. 

4.execute `stop activity` or `stop app` command.(Currently recommend use `stop activity` command)


### Q and A
1.while you start activity show warn as bellow:
```
Warning: Activity not started, its current task has been brought to the front
```
you can execute command `adb shell am force-stop com.ff.factoryimage` or `adb shell input keyevent KEYCODE_BACK` to resolve it before you start activity.
