package cn.bh.view.simple;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;
import cn.bh.jc.version.TimeVersion;

/**
 * 最后修改时间比较方式打包 注意，必须保持本地最新代码，因为要取本地tomcat下编译好的class,js等文件，本项目不能自动编译 resource
 * 会自动跳过，配置文件自己拷贝
 * 
 * @author liubq
 * @since 2017年12月21日
 */
public class PackerV1Main {

	public static void main(String[] args) {
		try {
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			String projectTomcat = "D:\\tomcat\\webapps\\ROOT";
			// 配置
			Config conf = new Config();
			// 工程地址
			List<TimeVersion> changeList = new ArrayList<TimeVersion>();
			changeList.add(new TimeVersion(conf, projectTomcat, "X:\\workspace\\xf_statis", "2018-11-10 23:59:59"));
			SysLog.log(" 开始支持请等待   ");
			// 根据版本取得差异文件
			DiffFileLister<TimeVersion> oper = new DiffFileLister<TimeVersion>(changeList);
			List<ChangeVO> list = oper.listChange();
			// 打包
			DiffFilePacker p = new DiffFilePacker("C:/Users/Administrator/Desktop", conf);
			p.pack(list);
			SysLog.log("\r\n 处理完成   ");
		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}

}
