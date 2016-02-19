### 依赖说明
---

本项目使用maven构建.若没有安装,请先到[官网][mvnurl]下载.

### maven安装,环境变量设置
---

1. Windows下面的安装

    下载[最新版][mvnurl],解压到你想保存的目录.  

2. 设置环境变量:  
	1. maven_home=maven解压的目录  
	2. 将%maven_home%\bin加入path环境变量中  

3. 测试maven是否成功

        mvn -v 

### 导入myeclipse注意事项
---

1. 在命令行中,进入项目根目录,运行
  
	mvn eclipse:eclipse  

2. myeclipse需要安装[m2eclipse][m2e]插件,替换内置的maven,内置的版本过老.

	[m2eclipse][m2e]插件的在线安装地址

		http://download.eclipse.org/technology/m2e/releases

3. 在myeclipse中file->import->existing maven project,选择项目目录.  

4. 选中项目,右键-->run as-->Maven build...-->在弹出来的对话框中的goals中填写

		jetty:run

	点击run,运行项目


### 项目使用数据库
---



### 文档
---


[mvnurl]: http://maven.apache.org/download.cgi
[m2e]: http://www.eclipse.org/m2e/
