<template>
  <div>
    <router-link to="/layout">66666666666666</router-link>
  </div>
</template>

<script>

export default {
  data() {
    return {

    }
  },
  components: {
  },
  methods:{
    addUser(){
      var _this=this
      console.log(JSON.stringify(_this.form))
      this.$axios({
        url: "user/addUser",
        method: "post",
        headers: {
          "Content-Type": "application/json;charset=UTF-8"
        },
        data:(_this.form),
      }).then(function (resp) {
          if(resp===true){
            alert("添加成功");
            _this.form={};
          }else {
            alert("用户已存在");
          }
          }).catch(function (err) {
        alert("发生错误"+ err.message);
      });
    },

    login(){
      var _this=this
      this.$axios({
        url: "/user/add",
        method: "post",
        data:this.$qs.stringify({
          username:_this.username}),
      })
          .then(function (resp) {
            _this.test = resp.data
            console.log(_this.test)
          }).catch(function (err) {
        alert("发生错误，错误原因是：" + err.message);
      });
    }
  }
}
</script>
<style>

.el-main {
  background-color: #8dafcd;
  color: #333;
  text-align: center;
  line-height: 160px;
}

.el-menu{

}
</style>