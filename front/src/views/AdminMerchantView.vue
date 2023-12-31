<template>
    <Navbar />
    <div class="merchant-info">
        <el-button @click="showAddMerchant()" type="success" size="large">新增商家</el-button>
    </div>

    <div v-if="showAddMerchantDialog" class="edit-dialog-overlay">
        <!-- 用户编辑表单 -->
        <div class="edit-dialog-container">
            <el-form ref="registerForm" :model="registerForm" label-width="80px" class="register-form" :rules="rules">
                <!-- Merchantname Field -->
                <el-form-item label="商户名" prop="name">
                    <el-input v-model="registerForm.name" placeholder="请输入用户名" prefix-icon="el-icon-merchant"></el-input>
                </el-form-item>

                <!-- Password Field -->
                <el-form-item label="密码" prop="password">
                    <el-input v-model="registerForm.password" placeholder="请输入密码" prefix-icon="el-icon-lock"></el-input>
                </el-form-item>

                <el-form-item label="地址" prop="addr">
                    <el-input v-model="registerForm.addr" placeholder="请输入地址" prefix-icon="el-icon-time"></el-input>
                </el-form-item>

                <!-- Register Button -->
                <div slot="footer" class="dialog-footer">
                    <el-button @click="showAddMerchantDialog = false">返回</el-button>
                    <el-button type="primary" @click="submitReg(registerForm)">新增</el-button>
                </div>
            </el-form>
        </div>
    </div>


    <div class="merchant-management">
        <el-table :data="pagedMerchants">
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="name" label="商户名"></el-table-column>
            <el-table-column prop="password" label="密码"></el-table-column>
            <el-table-column prop="addr" label="地址"></el-table-column>
            <el-table-column label="操作">
                <template #default="{ row }">
                    <el-button size="small" @click="editMerchant(row)">修改</el-button>
                    <el-button size="small" type="danger" @click="deleteMerchant(row.id)">删除</el-button>
                </template>
            </el-table-column>

        </el-table>
        <div class="pagination-container">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage"
                :page-size="pageSize" :total="merchants.length" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination>
        </div>

        <div v-if="editDialogVisible" class="edit-dialog-overlay">
            <!-- 用户编辑表单 -->
            <div class="edit-dialog-container">
                <el-form ref="currentEditMerchant" :model="currentEditMerchant" label-width="80px" class="register-form"
                    :rules="rules">
                    <!-- Merchantname Field -->
                    <el-form-item label="商户名" prop="name">
                        <el-input v-model="currentEditMerchant.name" placeholder="请输入用户名"
                            prefix-icon="el-icon-merchant"></el-input>
                    </el-form-item>

                    <!-- Password Field -->
                    <el-form-item label="密码" prop="password">
                        <el-input v-model="currentEditMerchant.password" placeholder="请输入密码"
                            prefix-icon="el-icon-lock"></el-input>
                    </el-form-item>

                    <el-form-item label="地址" prop="addr">
                        <el-input v-model="currentEditMerchant.addr" placeholder="请输入地址"
                            prefix-icon="el-icon-time"></el-input>
                    </el-form-item>

                    <!-- Register Button -->
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="editDialogVisible = false">返回</el-button>
                        <el-button type="primary" @click="submitEdit(currentEditMerchant)">提交</el-button>
                    </div>
                </el-form>
            </div>
        </div>
    </div>
</template>
  
<script>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import Navbar from '../components/UserNav.vue';
axios.defaults.baseURL = 'http://localhost:8080';
export default {
    components: {
        Navbar,
    },
    data() {
        return {
            merchants: [],
            editDialogVisible: false,
            showAddMerchantDialog: false,
            currentEditMerchant: {},
            pagedMerchants: [], // 存储当前页的用户数据
            currentPage: 1,
            pageSize: 10,
            registerForm: {
                name: '',
                password: '',
                addr: '',
            },
            rules: {
                name: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { max: 32, message: '用户名不能超过32字符', trigger: 'blur' },
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 8, message: '密码不少于8位', trigger: 'blur' },
                ],
                addr: [
                    { required: true, message: '请输入地址', trigger: 'blur' },
                    { max: 256, message: '地址不能超过256个字符', trigger: 'blur' }
                ],
            },
        };
    },
    methods: {
        async fetchMerchants() {
            const res = await axios.get('/merchants/allmerchant')
            console.log(res.data)
            this.merchants = res.data;
            this.updatePagedMerchants();
        },
        editMerchant(merchant) {
            this.currentEditMerchant = { ...merchant };
            this.editDialogVisible = true;
            console.log(this.currentEditMerchant);
            console.log(this.editDialogVisible);
        },
        deleteMerchant(merchantId) {
            axios.delete(`/merchants/${merchantId}`)
                .then(() => {
                    this.fetchMerchants(); // 重新获取用户列表
                    this.$message.success('删除商户成功')
                })
                .catch(error => {
                    console.error('删除用户失败:', error);
                });
        },
        submitEdit(merchant) {
            const id = merchant.id;
            axios.put(`/merchants/${id}`, merchant)
                .then(() => {
                    this.fetchMerchants(); // 重新获取用户列表
                    this.$message.success('修改商户成功')
                })
                .catch(error => {
                    console.error('修改用户失败:', error);
                });
            // 发送修改用户的请求
            // 更新成功后关闭对话框并刷新用户列表
            this.editDialogVisible = false;
            this.fetchMerchants();
        },
        updatePagedMerchants() {
            const startIndex = (this.currentPage - 1) * this.pageSize;
            const endIndex = startIndex + this.pageSize;
            this.pagedMerchants = this.merchants.slice(startIndex, endIndex);
            console.log(this.pagedMerchants);
        },
        handleSizeChange(newSize) {
            this.pageSize = newSize;
            this.updatePagedMerchants();
        },
        handleCurrentChange(newPage) {
            this.currentPage = newPage;
            this.updatePagedMerchants();
        },
        showAddMerchant() {
            this.showAddMerchantDialog = true;
        },
        submitReg(registerForm) {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    axios.post('/auth/merchantregister', this.registerForm)
                        .then((res) => {
                            console.log(res.data)
                            if (res.data.code === 200) {
                                this.$message.success('新增商家成功');
                                // 注册成功后跳转到登录页面
                                this.showAddMerchantDialog = false;
                                this.fetchMerchants();
                            } else {
                                this.$message.error(res.data.msg);
                            }
                        })
                        .catch((err) => {
                            console.log(err)
                            this.$message.error('注册失败，请检查信息是否符合要求');
                        });
                } else {
                    this.$message.error('请检查表单信息是否填写正确');
                }
            });

        }
    },
    mounted() {
        this.fetchMerchants();
    }
};
</script>
  
<style>
.edit-dialog-overlay {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    /* 确保遮罩层在其他内容之上 */
}

.edit-dialog-container {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
}

.dialog-footer {
    display: flex;
    justify-content: space-around;
    margin-top: 20px;
}

.merchant-management .pagination-container {
    display: flex;
    justify-content: center;
    margin-top: 50px;
    /* 调整与表格的距离 */
    margin-bottom: 50px;
}

.merchant-info {
    text-align: center;
    /* 居中对齐 */
    font-size: larger;
    /* 字体放大 */
    background-color: #cbdae9;
    /* 背景为灰色 */
    margin: 20px auto;
    /* 与上下内容保持一定距离 */
    padding: 20px;
    /* 内部填充 */
    border-radius: 10px;
    /* 圆角边框 */
}
.merchant-management{
    margin-left: 50px;
    margin-right: 50px;
    margin-top: 50px;
}
</style>
  