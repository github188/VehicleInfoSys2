package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.AreaDao;
import cn.jiuling.vehicleinfosys2.dao.CameraDao;
import cn.jiuling.vehicleinfosys2.dao.CameraGroupDao;
import cn.jiuling.vehicleinfosys2.exception.ServiceException;
import cn.jiuling.vehicleinfosys2.model.Area;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.CameraGroup;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.vo.CameraQuery;
import cn.jiuling.vehicleinfosys2.vo.CameraVO;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings({"unused", "unchecked"})
@Service("cameraService")
public class CameraServiceImpl implements CameraService {
    private Logger log = Logger.getLogger(CameraServiceImpl.class);
    @Resource
    private CameraDao cameraDao;
    @Resource
    private CameraGroupDao cameraGroupDao;
    @Resource
    private AreaDao areaDao;

    private static final Short STATUS_VALID = 1;
    private static final Short STATUS_INVALID = 0;

    private static final Short TYPE_OTHER = 0;
    private static final Short TYPE_BALL = 1;
    private static final Short TYPE_GUN = 2;

    private static final Short CAMERA_TYPE_BAYONET = 1;
    private static final Short CAMERA_TYPE_ETPC = 2;

    @Override
    public Pager query(CameraQuery c, Integer page, Integer rows) {
        return cameraDao.queryCamera(c, page, rows);
    }

    @Override
    public Pager list(Camera c, Integer page, Integer rows) {

        StringBuilder areaIds = null;
        if(!StringUtils.isEmpty(c.getRegion())) {
            Pager areap = areaDao.findAreaChildren(Integer.parseInt(c.getRegion()),null,null);
            List areaLst = areap.getRows();
            areaIds = new StringBuilder();
            for (int i = 0; i < areaLst.size(); i++) {
                Area a = (Area) areaLst.get(i);
                areaIds.append(a.getId()).append(",");
            }
            areaIds.deleteCharAt(areaIds.lastIndexOf(","));
        }
        Pager p = cameraDao.queryByAreaIds(c, areaIds, page, rows);
        List list = p.getRows();
        List listVO = new ArrayList();

        for (Object o : list) {

            CameraVO cameraVO = new CameraVO();
            Camera cam = (Camera) o;

            try {
                BeanUtils.copyProperties(cameraVO, cam);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            Integer cameraGroupId = cam.getCameraGroupId();
            if (cameraGroupId != null) {
                CameraGroup cameraGroup = cameraGroupDao.find(cameraGroupId);
                cameraVO.setCameraGroupName(cameraGroup.getName());
            }
            listVO.add(cameraVO);
        }
        p.setRows(listVO);

        return p;
    }

    @Override
    public void add(Camera c) {
        c.setStatus(STATUS_VALID);
        c.setType(TYPE_OTHER);
        c.setCameraType(TYPE_OTHER);
        cameraDao.save(c);
    }

    @Override
    public void remove(Camera c) {
        Camera _c = cameraDao.find(c.getId());
        cameraDao.delete(_c);
    }

    @Override
    public void update(Camera c) {
        cameraDao.update(c);
    }

    @Override
    public void updateCamera(Camera cam) {
        Camera c = cameraDao.find(cam.getId());
        try {
            BeanUtils.copyProperties(c, cam);
            cameraDao.update(c);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean valideName(String name) {
        Object o = cameraDao.findByProperty("name", name);
        return o == null;
    }

    @Override
    public Boolean valideName(Integer id, String name) {
        List list = cameraDao.findByPropertyList("name", name);
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            Camera c = (Camera) iterator.next();
            // 当存在不同id和同名对象时,则校验不通过
            if (c.getId().intValue() != id.intValue() && c.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void saveCameras(MultipartFile cameraInfo) {
        List<String> cameras;
        String camera = "";
        String arr[];
        String name;
        Double longitude;
        Double latitude;
        Camera c;
        try {
            InputStream in = cameraInfo.getInputStream();
            cameras = IOUtils.readLines(in, "utf-8");
            for (Iterator iterator = cameras.iterator(); iterator.hasNext(); ) {
                camera = (String) iterator.next();
                //去除BOM标记
                camera = removeBom(camera);
                arr = camera.split(",");
                name = arr[0];
                longitude = Double.valueOf(arr[1]);
                latitude = Double.valueOf(arr[2]);

                if (longitude < -180 || longitude > 180 || latitude < -90 || latitude > 90) {
                    throw new ServiceException();
                }

                c = new Camera();
                c.setName(name);
                c.setLongitude(longitude);
                c.setLatitude(latitude);
                c.setStatus(STATUS_VALID);
                cameraDao.save(c);
            }
        } catch (ServiceException e) {
            throw new ServiceException("经纬度数据有误", e);
        } catch(DataIntegrityViolationException e){
        	throw new ServiceException("监控点已导入，无需重复导入", e);
        } catch (Exception e) {
            throw new ServiceException("监控点数据有误", e);
        }
    }

    @Override
    public Camera fingById(Integer cameraId) {
        return cameraDao.find(cameraId);
    }

    @Override
    public String getFilepath(Camera c) {
        String path = c.getUrl();
        return path;
    }

    @Override
    public Camera findByName(String cameraName) {
        return cameraDao.findByName(cameraName);
    }
	
	@Override
    public List<Camera> getAll() {
        return cameraDao.getAll();
    }

    @Override
    public Object camLstTree() {
        List<Area> allAreaLst = areaDao.getAll();
        List<Tree> treeLst = new ArrayList<Tree>();
        List<Camera> camLst = cameraDao.getAll();
        List<Tree> camTree = null;
        List<Tree> allTreeLst = new ArrayList<Tree>();

        List<Tree> curSblingsList = new ArrayList<Tree>();
        List<Tree> nextSblingsList = new ArrayList<Tree>();
        Tree oneNode = null;

        int cLstLen = camLst.size();
        int aLstLen = allAreaLst.size();

        //Area转换成Tree
        int[] areaIdArr = new int[aLstLen];
        if (allAreaLst != null && aLstLen > 0) {
            Tree t = null;
            Area a = null;
            for (int i = 0; i < aLstLen; i++) {
                t = new Tree();
                a = allAreaLst.get(i);

                Integer id = a.getId();

                t.setId(id);
                areaIdArr[i] = id;

                t.setText(a.getName());
                t.setPid(a.getParentId());

                allTreeLst.add(t);

                t = null;
                a = null;
            }
            Arrays.sort(areaIdArr);
        }

        //camera转换成tree
        if (camLst != null && cLstLen > 0) {
            Tree t = null;
            Camera c = null;
            for (int i = 0; i < cLstLen; i++) {
                t = new Tree();
                c = camLst.get(i);

                if(aLstLen <= 0) {
                    t.setId(c.getId());
                } else {
                    t.setId(++areaIdArr[aLstLen-1]);
                }

                t.setText(c.getName());

                String region = c.getRegion();
                if (!StringUtils.isEmpty(region)) {
                    t.setPid(Integer.parseInt(region));
                } else {
                    t.setPid(0);
                }

                Map<String,Short> attrMap = new HashMap<String, Short>();
                attrMap.put("status", c.getStatus());
                attrMap.put("cameraId", c.getId().shortValue());
                t.setAttributes(attrMap);
                t.setIconCls("icon-tip");

                allTreeLst.add(t);
            }
            t = null;
            c = null;
        }


        treeLst = getAreaChildrenList(null, allTreeLst);
        // 初始化
        if (treeLst != null && treeLst.size() > 0) {
            camTree = new ArrayList<Tree>();
            for (int i = 0; i < treeLst.size(); i++) {
                oneNode = treeLst.get(i);
                camTree.add(oneNode);
                nextSblingsList.add(oneNode);
            }
        }

        // 生成部门树结构
        while (nextSblingsList.size() > 0) {
            curSblingsList = nextSblingsList;
            nextSblingsList = new ArrayList<Tree>();
            for (int i = 0; i < curSblingsList.size(); i++) {
                oneNode = curSblingsList.get(i);
                List<Tree> curChildren = new ArrayList<Tree>();
                treeLst = getAreaChildrenList(oneNode.getId(), allTreeLst);
                if (treeLst != null && treeLst.size() > 0) {
                    oneNode.setState("closed");
                    oneNode.setChildren(curChildren);
                    for (int j = 0; j < treeLst.size(); j++) {
                        oneNode = treeLst.get(j);
                        curChildren.add(oneNode);
                        nextSblingsList.add(oneNode);
                    }
                }
            }
        }

        return camTree;
    }

    private List<Tree> getAreaChildrenList(Integer treeId, List<Tree> treeList) {
        List<Tree> childrenList = new ArrayList();
        Tree oneTree = null;

        if (treeList == null || treeList.size() < 1) {
            return null;
        }

        for (int i = 0; i < treeList.size(); i++) {
            oneTree = treeList.get(i);
            if (treeId == null) {
                if (oneTree.getPid().intValue() == 0) {
                    childrenList.add(oneTree);
                }
            } else {
                if (oneTree.getPid().intValue() == treeId.intValue()
                /*&& oneArea.getId().intValue() != treeId.intValue()*/) {
                    childrenList.add(oneTree);
                }
            }
        }
        return childrenList;
    }
    
    /**
     * 字符串去除开头BOM标记
     */
    private String removeBom(String soureStr){
    	try {
			byte[] allBytes = soureStr.getBytes("UTF-8");
			if(allBytes!=null && allBytes.length>=3){
				String hexString0 = Integer.toHexString(allBytes[0]);
				hexString0 = hexString0.substring(hexString0.length() -2);
				
				String hexString1 = Integer.toHexString(allBytes[1]);
				hexString1 = hexString1.substring(hexString1.length() -2);
				
				String hexString2 = Integer.toHexString(allBytes[2]);
				hexString2 = hexString2.substring(hexString2.length() -2);
				
				if(hexString0.equalsIgnoreCase("EF") && hexString1.equalsIgnoreCase("BB") && hexString2.equalsIgnoreCase("BF")){
					byte[] nStr = new byte[allBytes.length-3];
					System.arraycopy(allBytes, 3, nStr, 0, nStr.length);
					soureStr = new String(nStr);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	return soureStr;
    }
}
