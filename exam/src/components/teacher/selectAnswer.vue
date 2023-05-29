//查询所有题库
<template>
  <div class="exam">
    <div>
      <el-input
        placeholder="请输入要查的题目"
        v-model="question"
        style="width:200px"
        clearable>
      </el-input>
      <el-button
        type="primary"
        size="small"
        icon="el-icon-search"
        style="margin-left:1rem"
        @click="searchAnswer"
      >
        搜索
      </el-button>
    </div>
    <br>
    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="subject" label="试卷名称" width="180"></el-table-column>
      <el-table-column prop="question" label="题目信息" width="490" show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="section" label="所属章节" width="200"></el-table-column>
      <el-table-column prop="type" label="题目类型" width="200"
                       sortable
                       column-key="type"
                       :filters="filter"
                       :filter-method="filterHandler">
      </el-table-column>
      <el-table-column prop="score" label="试题分数" width="150"></el-table-column>
      <el-table-column prop="level" label="难度等级" width="133"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="edit(scope.row.questionId)" type="primary" size="small">编辑</el-button>
          <el-button @click="deleteRecord(scope.row.questionId)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10, 100]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page"
    ></el-pagination>
    <!-- 编辑对话框-->
    <el-dialog
      title="编辑考题信息"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <section class="update">
        <el-form ref="form" :model="form" label-width="80px">
          <el-form-item label="试卷名称">
            <el-input v-model="form.subject"></el-input>
          </el-form-item>
          <el-form-item label="题目信息">
            <el-input v-model="form.question"></el-input>
          </el-form-item>
          <el-form-item label="所属章节">
            <el-input v-model="form.section"></el-input>
          </el-form-item>
          <el-form-item label="试题分数">
            <el-input v-model="form.score"></el-input>
          </el-form-item>
          <el-form-item label="难度等级">
            <el-input v-model="form.level"></el-input>
          </el-form-item>
        </el-form>
      </section>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      question: "",
      form: {}, //保存点击以后当前题库的信息
      pagination: {
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      dialogVisible: false,
      types: [], //
      filter: null //过滤参数
    };
  },
  created() {
    this.getAnswerInfo();
  },
  methods: {
    searchAnswer(){
      this.getAnswerInfoList();
    },

    getAnswerInfo() {
      //分页查询所有试卷信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}`
      )
        .then(res => {
          this.pagination = res.data.data;
          // console.log(res);
          this.types = res.data.data.records
          let mapVal = this.types.map((element,index) => { //通过map得到 filter:[{text,value}]形式的数组对象
            let newVal = {}
            newVal.text = element.type
            newVal.value = element.type
            return newVal
          })
          let hash = []
          const newArr = mapVal.reduce((item, next) => { //对新对象进行去重操作
            hash[next.text] ? '' : hash[next.text] = true && item.push(next);
            // console.log(item);
            return item
          }, []);
          this.filter = newArr
        })
        .catch(error => {});
    },
    getAnswerInfoList() {
      //分页查询指定试卷信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}/${this.question}`
      )
        .then(res => {
          this.pagination = res.data.data;
          console.log(res);
        })
        .catch(error => {});
    },

    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getAnswerInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getAnswerInfo();
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    },

    edit(questionId) { //编辑试卷
      this.dialogVisible = true
      this.$axios(`/api/multiQuestion/${questionId}`).then(res => { //根据题目id请求后台
        if(res.data.code == 200) {
          this.form = res.data.data
        }
      })
    },
    handleClose(done) { //关闭提醒
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        }).catch(_ => {});
    },
    submit() { //提交修改后的试卷信息
      this.dialogVisible = false
      this.$axios({
        url: '/api/multiQuestion',
        method: 'put',
        data: {
          ...this.form
        }
      }).then(res => {
        if(res.data.code == 200) {
          this.$message({ //成功修改提示
            message: '更新成功',
            type: 'success'
          })
        }
        this.getAnswerInfo()
      })
    },
    deleteRecord(questionId) {
      this.$confirm("确定删除该记录吗,该操作不可逆！！！","删除提示",{
        confirmButtonText: '确定删除',
        cancelButtonText: '算了,留着',
        type: 'danger'
      }).then(()=> { //确认删除
        this.$axios({
          url: `/api/multiQuestion/${questionId}`,
          method: 'delete',
        }).then(res => {
          this.getAnswerInfo()
        })
      }).catch(() => {

      })
    },
    formatter(row, column) {
      return row.address;
    },
    filterTag(value, row) {
      return row.tag === value;
    },
    filterHandler(value, row, column) {
      const property = column["property"];
      return row[property] === value;
    },
  }
};
</script>
<style lang="less" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #DD5862 !important;
  }
}
  .el-table .warning-row {
    background: #000 !important;

  }

  .el-table .success-row {
    background: #DD5862;
  }

</style>
