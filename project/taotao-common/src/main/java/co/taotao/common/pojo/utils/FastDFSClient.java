package co.taotao.common.pojo.utils;

public class FastDFSClient {
	
	/*// 3、创建一个TrackerClient对象。
	private TrackerClient trackerClient = null;
	// 4、创建一个TrackerServer对象。
	private TrackerServer trackerServer = null;
	// 5、声明一个StorageServer对象，null。
	private StorageServer storageServer = null;
	// 6、获得StorageClient对象。
	private StorageClient storageClient = null;
	public FastDFSClient(String config){
		ClientGlobal.init(config);
		this.trackerClient = new TrackerClient();
		this.trackerServer = this.trackerClient.getConnection();
		this.storageClient = new StorageClient(this.trackerServer,this.storageServer);
	}
	
	public String upLoad(String filename, String extname, NameValuePair[] metas){
		String result = storageClient.upload_file1(filename,extname,metas);
		return result;
	}
	
	public String uploadFile(String fileName){
		return uploadFile(fileName,null,null);
	}
	
	public String uploadFile(String fileName, String extName){
		return uploadFile(fileName, extName,null);
	}
	
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas){
		String result = storageClient.upload_file1(fileContent,extName,metas);
		return result;
	}
	
	public String uploadFile(byte[] fileContent){
		return storageClient.upload_file1(fileContent,extName,metas);
	}
	
	public String uploadFile(byte[] fileContent, String extName){
		return storageClient.upload_file1(fileContent,extName,null);
	}*/
}
