<template>
  <div>
    <ManageHeader/>
    <div style="display: flex;">
      <ManageAside />
      <router-view style="flex: 1"/>
    </div>
  </div>
</template>

<script>

import ManageHeader from "@/components/ManageHeader";
import ManageAside from "@/components/ManageAside";

export default {
  name: "Layout",
  data:function (){
    return{

    }
  },
  components:{
    ManageHeader,
    ManageAside,
  },

}
</script>

<style scoped>

</style>