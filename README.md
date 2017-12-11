# SimpleModelforAndroid
**MVC+MVP+MVVM 项目实例**</br>
## 1.MVC</br>
MVC全名 Model View Controller </br>
模型（model）-视图（view）-控制器（controller）</br>
* M是指业务模型</br>
* V是指用户界面</br>
* C则是控制器</br>
其中 View 层其实就是程序的 UI 界面，用于向用户展示数据以及接收用户的输入,而 Model 层就是 JavaBean 实体类，用于保存实例数据Controller 控制器用于更新 UI 界面和数据实例</br>
![Image of Yaktocat](https://github.com/wycgit2016/SimpleModelforAndroid/blob/master/dexImage/mvc.png)
</br>

## 2.MVP</br>
MVP是一种经典的模式</br>
M代表Model-V代表View-P则是Presenter（Model和View之间的桥梁）</br>
* MVP模式的核心思想:
* 把Activity中的UI逻辑抽象成View接口，把业务逻辑抽象成Presenter接口，Model类还是原来的Model类</br>
* 作用:
* 1.分离视图逻辑和业务逻辑，降低耦合</br>
* 2.Activity只处理生命周期的任务，代码简洁</br>
* 3.视图逻辑和业务逻辑抽象到了View和Presenter中，提高阅读性</br>
* 4.Presenter被抽象成接口，可以有多种具体的实现</br>
* 5.业务逻辑在Presenter中，避免后台线程引用Activity导致内存泄漏</br>
![Image of Yaktocat](https://github.com/wycgit2016/SimpleModelforAndroid/blob/master/dexImage/mvp.png)
</br>

## 3.MVVM</br>
MVVM模式包含三个部分</br>
Model代表基本的业务逻辑-View显示内容-ViewModel将前面两者联系在一起</br>
* Android Data Binding:
* 2015年I/O大会上谷歌介绍的工具，该工具可以将View和一个对象的field绑定，当field更新的时候，framework将收到通知，然后View自动更新</br>
* Data Binding官方原生支持MVVM模型可以让我们在不改变现有代码的框架下，非常容易的使用这些新特性</br>
* 原理:
* MVVM模式中，一个ViewModel和一个View匹配，它没有MVP中的IView接口，而是完全的和View绑定，所有View中的修改变化，都会自动更新到ViewModel中，同时ViewModel的任何变化也会自动同步到View上显示</br>
![Image of Yaktocat](https://github.com/wycgit2016/SimpleModelforAndroid/blob/master/dexImage/mvvm.png)
