<template>
  <div>
    <div style="margin:30px">
      <!-- 功能区 -->
      <div>
      <el-button type="primary" @click="dialogInsert = true">新增</el-button>
      <el-input placeholder="按照以下属性查询" v-model="input" style="width: 300px;margin:0px 5px 0px 30px"/>
      <el-button icon="el-icon-search" circle type="primary" @click="findBook"></el-button>
      </div>
      <el-radio-group v-model="radio" style="margin-left:100px">
        <el-radio label="isbn">ISBN</el-radio>
        <el-radio label="bookName">书名</el-radio>
        <el-radio label="author">作者</el-radio>
      </el-radio-group>
    </div>

    <!-- 表格 -->
    <el-table :data="listBooks.slice((currentPage - 1) * pagesize, currentPage * pagesize)" border height="calc(100vh - 240px)"
        style="width: 90%;margin-left: 5%" >
      <el-table-column prop="isbn" label="ISBN"/>
      <el-table-column prop="bookName" label="书名"/>
      <el-table-column prop="author" label="作者"/>
      <el-table-column prop="price" label="价格" sortable/>
      <el-table-column prop="publishTime" label="出版时间"/>
      <el-table-column label="封面">
        <template slot-scope="scope">
          <img :src=scope.row.cover style="width:80px;height:80px"/>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="200">
        <template slot-scope="scope">
          <el-button size="mini" @click="dialogUpdate=true,book=listBooks[scope.$index]"> 修改 </el-button>
          <el-button size="mini" @click="dialogDelete=true,book=listBooks[scope.$index]"> 删除 </el-button>
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
      :page-sizes="[4, 8, 16, 32]"
      :page-size="pagesize"
      layout="total, sizes, prev, pager, next"
      :total="listBooks.length">
    </el-pagination>
    <!-- 增加图书界面 -->
    <el-dialog title="增加图书" :visible.sync="dialogInsert" width="500px" center>
      <el-form :model="form" label-position="left" label-width="80px" >
          <el-form-item label="ISBN">
            <el-input v-model="form.isbn"/>
          </el-form-item>
          <el-form-item label="书名">
            <el-input v-model="form.bookName"/>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="form.author"/>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="form.price"/>
          </el-form-item>
          <el-form-item label="出版日期">
            <el-date-picker v-model="form.publishTime" type="date" value-format="yyyy-MM-dd"
              :picker-options="pickerOptions"/>
          </el-form-item>
          <el-form-item label="封面">
            <el-upload name="file" :limit="1"
              action="http://localhost:8080/springmvc/file/upload"
              :on-success="fileSuccess"
              :before-upload="beforeAvatarUpload">
              <el-button size="small" type="primary">上传<i class="el-icon-upload el-icon--right"></i></el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="insertBook">添加图书</el-button>
          <el-button @click="dialogInsert = false">取 消</el-button>
        </div>
    </el-dialog>
    <!-- 删除提示界面 -->
    <el-dialog title="提示" :visible.sync="dialogDelete" width="30%" append-to-body>
      <span>确定要删除ISBN为：{{book.isbn}},书名为{{book.bookName}}</span>
      <span slot="footer">
        <el-button type="primary" @click="deleteBook(book.isbn)">确 定</el-button>
        <el-button @click="dialogDelete = false">取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改图书界面 -->
    <el-dialog title="修改图书" :visible.sync="dialogUpdate" width="500px" center>
      <el-form :model="book" label-position="left" label-width="80px" >
          <el-form-item label="ISBN">
            <el-input v-model="book.isbn" disabled/>
          </el-form-item>
          <el-form-item label="书名">
            <el-input v-model="book.bookName"/>
          </el-form-item>
          <el-form-item label="作者">
            <el-input v-model="book.author"/>
          </el-form-item>
          <el-form-item label="价格">
            <el-input v-model="book.price"/>
          </el-form-item>
          <el-form-item label="出版日期">
            <el-date-picker v-model="book.publishTime" type="date" value-format="yyyy-MM-dd"
              :picker-options="pickerOptions"/>
          </el-form-item>
          <el-form-item label="封面">
            <el-upload name="file" :limit="1"
              action="http://localhost:8080/springmvc/file/upload"
              :on-success="fileSuccess"
              :before-upload="beforeAvatarUpload">
              <el-button size="small" type="primary">修改<i class="el-icon-upload el-icon--right"></i></el-button>
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer">
          <el-button type="primary" @click="updateBook">修改图书</el-button>
          <el-button @click="dialogUpdate = false">取 消</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ManageBook",
  data(){
    return{
      input:'', 
      radio:'isbn',
      listBooks:[],           //存放表格中的图书
      book:{},                //表格中获取的图书
      rootFilePath:'',        //存放文件上传后的url
      form:{                  //表单
        isbn:'',
        bookName:'',
        author:'',
        price:0,
        publishTime:'',
        cover:'',
      },
      dialogInsert:false,   //控制显示增加界面
      dialogDelete:false,   //控制显示删除界面
      dialogUpdate:false,   //控制显示修改界面
      currentPage: 1,       //初始页
      pagesize: 4,          // 每页的数据
      total: 0,             //总数
      pickerOptions: {      //日期禁用
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
    }
  },
  methods:{
    //修改每页的数据
    handleSizeChange(size) {
      this.pagesize = size;
    },
    // 修改页数
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
    },
    // 查找全部图书
    listBook(){
      var _this = this;
      this.$axios({
        url: "book/listBook",
        method: "post",
      }).then(function (resp) {
            _this.listBooks = resp.data
      }).catch(function (err) {
        alert(err.message);
      });
    },
    // 增加图书
    insertBook:function(){
      var _this = this
      if(_this.rootFilePath!==''){
        _this.form.cover = _this.rootFilePath
      }
      this.$axios({
        url:"book/addBook",
        method:"post",
        data:(_this.form)
      }).then(function (resp) {
        if(resp.data===true){
          _this.dialogInsert=false
          _this.$message.success("添加成功")
          _this.listBook()
          _this.form={}
        }else {
          _this.$message.warning("图书已存在")
        }
      }).catch(function (err) {
        alert(err.message);
      });
    },
    // 删除图书
    deleteBook(isbn){
      var _this = this;
      _this.$axios({
        url:"book/deleteBook",
        method:"post",
        data:(isbn)
      }).then(function(resp){
        if(resp.data===true){
          _this.$message.success("删除成功")
          _this.listBook()
          _this.dialogDelete=false
        }
      }).catch(function (err) {
        alert(err.message);
      });
    },
    // 修改图书
    updateBook(){
      var _this = this
      if(_this.rootFilePath!==''){
        _this.book.cover = _this.rootFilePath
      }
      _this.$axios({
        url:"book/updateBook",
        method:"post",
        data:(_this.book)
      }).then(function(resp){
        if(resp.data===true){
          _this.dialogUpdate=false
          _this.$message.success("修改成功")
          _this.listBook()
        }
      }).catch(function(err){
        alert(err.message)
      })
    },
    // 查找图书
    findBook(){
      var _this = this
      if(_this.input===''){
        _this.listBook()
        return true
      }
      _this.$axios({
        url: "book/findBook",
        method: "post",
        data:({input:_this.input,radio:_this.radio})
      }).then(function (resp) {
            _this.listBooks = resp.data
      }).catch(function (err) {
        alert(err.message);
      });
    },
    //文件上传成功函数
    fileSuccess(resp,file){
      var _this = this
      _this.rootFilePath= file.response
    },
    //文件上传前执行
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt10M = file.size / 1024 / 1024 < 10;
      if (!isJPG&&!isPNG) {
        this.$message.error('上传头像图片只能是JPG/PNG/格式!');
        return false;
      }
      if (!isLt10M) {
        this.$message.error('上传头像图片大小不能超过10MB!');
        return false;
      }
      return true;
    }
  },
  created:function(){
    this.listBook()
  },
}
</script>
