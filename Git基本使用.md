创建代码仓库
git init

添加文件，例如添加 README.md
git add README.md

添加目录，在 add 后面加上目录名字
git add src

添加所有文件
git add .

提交
git commit -m "（必须带有描述信息，否则被认为不合法）"

查看所有分支
git branch -a

创建分支
git branch version1.0

删除分支
git branch -D version1.0

切换分支
git checkout version1.0

master将version1.0合并过来
git checkout master
git merge version1.0

克隆远程仓库到本地
git clone https://github.com/example/test.git

同步本地内容到远程仓库
git push https://github.com/example/test.git master

同步远程仓库到本地，不会合并到任何分支，会存放到 https://github.com/example/test.git/master 分支上
git fetch https://github.com/example/test.git master

查看远程仓库修改
git diff https://github.com/example/test.git/master

pull相当于fetch和merge命令一起执行
git pull https://github.com/example/test.git master