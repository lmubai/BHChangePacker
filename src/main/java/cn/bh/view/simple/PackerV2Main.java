package cn.bh.view.simple;

import java.util.ArrayList;
import java.util.List;

import cn.bh.jc.DiffFileLister;
import cn.bh.jc.DiffFilePacker;
import cn.bh.jc.common.SysLog;
import cn.bh.jc.domain.ChangeVO;
import cn.bh.jc.domain.Config;
import cn.bh.jc.version.SVNVersion;
import cn.bh.jc.version.vo.SvnParaVO;

/**
 * SVN方式打包 注意，必须保持本地最新代码，因为要取本地tomcat下编译好的class,js等文件，本项目不能自动编译 resource
 * 会自动跳过，配置文件自己拷贝
 * 
 * @author liubq
 * @since 2017年12月21日
 */
public class PackerV2Main {

	public static void main(String[] args) {
		try {
			// 工程所用的tomcat地址（主要是为了Copy class等文件）
			// 配置
			Config conf = new Config();
			// 排除配置文件
			// conf.addExclusiveFileExt(".properties");

			List<SVNVersion> chageList = new ArrayList<SVNVersion>();
			chageList.add(new SVNVersion(conf, buildStaticVO()));
			SysLog.log("开始执行请等待。。。。。。  ");
			// 根据版本取得差异文件
			DiffFileLister<SVNVersion> oper = new DiffFileLister<SVNVersion>(chageList);
			List<ChangeVO> list = oper.listChange();
			// 打包
			DiffFilePacker p = new DiffFilePacker("G:\\packer", conf);
			p.pack(list);
			SysLog.log("\r\n 处理完成 。。。。。。    ");
		} catch (Exception e) {
			SysLog.log("异常", e);
		}
	}

	public static SvnParaVO buildStaticVO() {
		String url = "https://10.65.12.21/svn/product/pwlp/branch/pwlp-hubei-cluemng";
		String username = "lixiaolong";
		String password = "123456";
		String target = "E:\\IDEAProject\\pwlp\\pwlp-hubei\\target\\pwlp-1.0.0";
		Long startVersion = 30050L;
		return new SvnParaVO(url, username, password, startVersion, target).setExpName("pwlp");
	}
}
