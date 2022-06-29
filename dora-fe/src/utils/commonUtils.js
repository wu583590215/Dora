import axios from "axios";

export default {
    // 获取码值信息
    getCodeLibrary: function (codeTypes,fun) {
        axios.post("code/getLibrary", codeTypes).then(fun);
    }
}