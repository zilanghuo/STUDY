package com.mouse.study.test.growing;

import com.mouse.study.api.utils.DateUtils;
import com.mouse.study.utils.*;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lwf on 2017/8/18.
 * use to do:
 * 项目ID  AI  b93a6913b8d3edb9
 * 项目私钥  BesparUPrayegEd4faReTAVUjuqAka7u
 * 项目公钥（X-Client-Id）: b8t77weRaMeDruMAtrAhUPU8AjaphaVe
 * 项目UID ：1P6VAXPd
 * <p>
 * 身份验签：  b8t77weRaMeDruMAtrAhUPU8AjaphaVe  42702cac79e509375c3c8c98cd70551e7945cbdc5aff83044648d048d081b4a9
 * <p>
 * <p>
 * <p>
 * 42702cac79e509375c3c8c98cd70551e7945cbdc5aff83044648d048d081b4a9
 * 59ac15d096d025eb815e63c61d1246e7ebce3b0e5ae34de5fad7c0b52b3ac264
 * <p>
 */
public class Test {

    public static String url_token = "https://www.growingio.com/auth/token";

    public static String url_get_data_remote = "https://www.growingio.com/insights/b93a6913b8d3edb9/2017082108.json";

    public static String url_get_data = "https://www.growingio.com/insights";

    public static String fileUrl = "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_page_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T072258Z&X-Amz-Expires=6000&X-Amz-SignedHeaders=host&X-Amz-Signature=41aa8bcd93bff497d5cd55fe47ef41f1e1a0a9029e8a64f639cd755c5f21152b";

    public static String id = "b93a6913b8d3edb9";

    public static String BASE_DIR = "f://growing//";

    public static void main(String[] args) throws Exception {
     //   String result = getData();
        String date = DateUtils.format(new Date(), DateUtils.YYYY_FORMAT);
   /*     DataResult dateResult = (DataResult) JackJsonUtil.strToObj(result, DataResult.class);
        for (String url : dateResult.getDownlinks()) {
            String[] split = url.split(id);
            String fileName = split[1].substring(1, split[1].indexOf("?"));
            //对应的地址，按时间下载
            FileUtil.saveUrlFile("f://growing//" + date + "//" + fileName, url);
        }*/
        //遍历当前时间点的文件，解压，然后解读

        //获取gz地址
        String currentDir = BASE_DIR + date;
        ArrayList<String> gzFiles = new ArrayList();
        FileUtils.traverseFolder(currentDir,gzFiles);
        //解压
        for (String file : gzFiles){
            System.out.println(file);
            ZipUtils.decompress(new File(file),false);
            //读取
            System.out.println("----------------------------------------------------------");
            readDate(file.substring(0,file.length()-3));
        }

    }


    /**
     * 读取文件
     *
     * @throws Exception
     */
    public static void readForGZ() throws Exception {
        String fileName = "F:\\part-remote";
        String FileContent = "";
        FileInputStream fis = new FileInputStream(fileName);
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line = br.readLine()) != null) {
            FileContent += line;
            FileContent += "\r\n"; // 补上换行符
            System.out.println(line);
        }
    }

    /**
     * 读取远程数据接口
     */
    public static void readDate(String fileName) {
        String FileContent = ""; // 文件很长的话建议使用StringBuffer
        try {
            FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                FileContent += line;
                FileContent += "\r\n"; // 补上换行符
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据接口
     * 获取的结果为：{"status":"FINISHED","downlinks":[
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_page_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T072258Z&X-Amz-Expires=6000&X-Amz-SignedHeaders=host&X-Amz-Signature=41aa8bcd93bff497d5cd55fe47ef41f1e1a0a9029e8a64f639cd755c5f21152b",
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_visit_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T072258Z&X-Amz-Expires=6000&X-Amz-SignedHeaders=host&X-Amz-Signature=e7b9a91f654488f8774244aba53f5fc7ca989d17c693df6252ddc128c387f78d",
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_action_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T072258Z&X-Amz-Expires=6000&X-Amz-SignedHeaders=host&X-Amz-Signature=bf6b8adbe5329ee9c46420f18043d6e1073e468b396eae204a6704ef975e037c",
     * "https://growing-insights.s3.cn-north-1.amazonaws.com.cn/ddbf_b93a6913b8d3edb9_action_tag_201708210000/part-00000.gz?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAP2ISDXVVNV53ECSQ/20170821/cn-north-1/s3/aws4_request&X-Amz-Date=20170821T072258Z&X-Amz-Expires=6000&X-Amz-SignedHeaders=host&X-Amz-Signature=4665aa05d8961f008776adab294207c2a64ec149bae120caa9e89cb833b75ced"]}
     */
    public static String getData() {

        String ai = "b93a6913b8d3edb9";
        String date = "2017082108";
        String url_data = url_get_data + "/" + ai + "/" + date + ".json";
        Map<String, String> valueMap = new HashMap();

        valueMap.put("expire", "100");
        Map<String, String> headMap = new HashMap();
        headMap.put("X-Client-Id", "b8t77weRaMeDruMAtrAhUPU8AjaphaVe");
        headMap.put("Authorization", "WNKGA8tpdS7KD2CxwboaBrGxKzOSCd15YkWMcZW84l8qhlYHV2qbHaKsLjcqiSXL");

        HttpsClientUtils clientUtils = new HttpsClientUtils();
        try {
            return clientUtils.get(url_data, valueMap, headMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取数据
     */
    public static void getDateForHour() {
        Map<String, String> headMap = new HashMap();
        headMap.put("X-Client-Id", "b8t77weRaMeDruMAtrAhUPU8AjaphaVe");
        headMap.put("Authorization", "yvHDiQlWQOirrqG7MQnCJQcJfTYJYURmVHtDauCRs5yFDwevmwl6TMAtg6i8EYKx");
        Map<String, String> valueMap = new HashMap();
        //  valueMap.put("ai", "b93a6913b8d3edb9");
        //  valueMap.put("date", "2017082108");
        valueMap.put("expire", "1");
        try {
            HttpsClientUtils.get(url_get_data, valueMap, headMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * growing 接口认证
     * yvHDiQlWQOirrqG7MQnCJQcJfTYJYURmVHtDauCRs5yFDwevmwl6TMAtg6i8EYKx
     */
    private static void testToken() {
        Map<String, String> valueMap = new HashMap();
        Map<String, String> headMap = new HashMap();
        headMap.put("X-Client-Id", "b8t77weRaMeDruMAtrAhUPU8AjaphaVe");

        valueMap.put("ai", "b93a6913b8d3edb9");
        valueMap.put("project", "1P6VAXPd");
        valueMap.put("tm", "1503368721219");
        valueMap.put("auth", "59ac15d096d025eb815e63c61d1246e7ebce3b0e5ae34de5fad7c0b52b3ac264");
        try {
            HttpsClientUtils.post(url_token, valueMap, headMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * {"status":"success","code":"5axRCtECNqNZ6c9sNuxpWnoihYr8VEr0oxYA0Q5ga7cSM2QYy3jkuoT4pR5Ryako"}Resp Code:200
         */


    }


    public static String authToken(String secret, String project, String ai, Long tm) throws Exception {
        String message = "POST\n/auth/token\nproject=" + project + "&ai=" + ai + "&tm=" + tm;
        Mac hmac = Mac.getInstance("HmacSHA256");
        hmac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signature = hmac.doFinal(message.getBytes("UTF-8"));
        return Hex.encodeHexString(signature);
    }
}
