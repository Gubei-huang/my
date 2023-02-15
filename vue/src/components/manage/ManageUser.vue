<template>
  <div>
    <div>
      <!-- 功能区 -->
      <el-button type="primary" @click="dialogInsert = true" style="margin: 30px">新增</el-button>
      <el-input placeholder="输入用户名查询" v-model="input" style="width: 200px;margin:20px;margin-right:5px"/>
      <el-button icon="el-icon-search" circle @click="findUser" type="primary"></el-button>
    </div>

    <!-- 表格 -->
    <el-table :data="listUsers.slice((currentPage - 1) * pagesize, currentPage * pagesize)" border height="calc(100vh - 240px)"
        style="width: 90%;margin-left: 5%">
      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="password" label="密码"/>
      <el-table-column prop="age" label="年龄" sortable/>
      <el-table-column prop="gender" label="性别"/>
      <el-table-column fixed="right" label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" @click="dialogUpdate = true,user = listUsers[scope.$index]"> 修改 </el-button>
          <el-button size="mini" @click="dialogDelete = true,user = listUsers[scope.$index]"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页按钮 -->
    <el-pagination
      style="width: 90%;margin-left: 5%;margin-top:20px"
      background
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[8, 16, 32, 60]"
      :page-size="pagesize"
      layout="total, sizes, prev, pager, next"
      :total="listUsers.length">
    </el-pagination>

    <!-- 新增用户界面 -->
    <el-dialog title="新增用户" :visible.sync="dialogInsert" width="500px" center>
      <el-form :model="form" label-width="80px" :rules="rules">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" prefix-icon="el-icon-user" style="width:300px"/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" prefix-icon="el-icon-lock" style="width:300px"/>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="form.age" style="width:300px" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio v-model="form.gender" label="男">男</el-radio>
          <el-radio v-model="form.gender" label="女">女</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addUser">确 定</el-button>
        <el-button @click="dialogInsert = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 删除提示界面 -->
    <el-dialog title="提示" :visible.sync="dialogDelete" width="30%" append-to-body>
      <span>确定要删除用户名为：{{user.username}}</span>
      <span slot="footer">
        <el-button type="primary" @click="deleteUser">确 定</el-button>
        <el-button @click="dialogDelete = false">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改用户界面 -->
    <el-dialog title="修改信息" :visible.sync="dialogUpdate" width="500px" center>
      <el-form :model="user" label-width="80px" :rules="rules">
        <el-form-item label="用户名:">
          <el-input v-model="user.username" prefix-icon="el-icon-user" style="width:300px" disabled/>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" prefix-icon="el-icon-lock" style="width:300px"/>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input v-model="user.age" style="width:300px" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio v-model="user.gender" label="男">男</el-radio>
          <el-radio v-model="user.gender" label="女">女</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="updateUser">确 定</el-button>
        <el-button @click="dialogUpdate = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data(){
    return{
      input:'',              //搜索输入框
      form:{                  //表单
        username:'',
        password:'',
        age:'',
        gender:'男',
      },
      listUsers:[],         //表格中所有用户
      user:{},              //表格中获取存放的用户
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
        ],
        age: [
          { type: 'number', message: '年龄必须为数字值'}
        ],
      },
      dialogInsert:false,   //控制显示增加界面
      dialogDelete:false,   //控制显示删除界面
      dialogUpdate:false,   //控制显示修改界面
      currentPage: 1,       //初始页
      pagesize: 8,          // 每页的数据
      total: 0,             //总数
    }
  },
  methods:{
    //修改每页的数据
    handleSizeChange: function (size) {
      this.pagesize = size;
    },
    // 修改页数
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage;
    },
    //返回所有用户
    listUser:function(){
      var _this=this
      this.$axios({
        url: "user/listUser",
        method: "post",
      }).then(function (resp) {
            _this.listUsers = resp.data
      }).catch(function (err) {
        alert(err.message);
      });
    },
    // 增加用户
    addUser:function(){
      var _this=this
      if(_this.form.username.length<3){
        _this.$message.error("用户名不能少于三位");
        return false;
      }
      if(_this.form.password.length<3){
        _this.$message.error("密码不能少于三位");
        return false;
      }
      if(_this.form.age>120){
        _this.$message.error("最大年龄为120");
        return false;
      }
      this.$axios({
        url: "user/addUser",
        method: "post",
        data:(_this.form),
      }).then(function (resp) {
        if(resp.data===true){
          _this.form={};
          _this.form.gender="男";
          _this.listUser();
          _this.dialogInsert = false;
          _this.$message.success("添加成功")
        }else {
          _this.$message.warning("用户已存在")
        }
      }).catch(function (err) {
        alert(err.message);
      });

    },
    // 查询用户
    findUser:function(){
      var _this=this
      if(!_this.input){
        _this.listUser();
        return true;
      }
      this.$axios({
        url: "user/findUser",
        method: "post",
        data:(_this.input),
      }).then(function (resp) {
        _this.listUsers = resp.data
      }).catch(function (err) {
        alert(err.message);
      });
    },
    // 删除用户
    deleteUser:function (){
      var _this = this;
      _this.$axios({
        url:"user/deleteUser",
        method:"post",
        data:(_this.user.username)
      }).then(function (resp){
        _this.listUser()
        _this.dialogDelete = false
          _this.$message.success("已删除")
      }).catch(function (err){
        alert(err.message);
      })
    },
    // 修改用户
    updateUser:function (){
      var _this = this;
      if(_this.form.password.length<3){
        _this.$message.error("密码不能少于三位");
        return false;
      }
      if(_this.form.age>120){
        _this.$message.error("最大年龄为120");
        return false;
      }
      _this.$axios({
        url:"user/updateUser",
        method:"post",
        data:(_this.user)
      }).then(function (resp){
        _this.listUser();
        _this.dialogUpdate = false;
          _this.$message.success("添加成功")
      }).catch(function (err){
        alert(err.message);
      })
    },
  },
  
  // 界面初始化方法
  created:function () {
    this.listUser();
  }
}
</script>
