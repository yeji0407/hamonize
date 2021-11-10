package com.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mapper.IUserMapper;
import com.model.OrgVo;
import com.model.UserVo;
import com.paging.PagingUtil;
import com.service.OrgService;
import com.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserMapper userMapper;

	@Autowired
	private OrgService oService;

	@Autowired
	private UserService userSerivce;

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	/*
	 * 사용자관리 페이지
	 * 
	 * @return
	 * 
	 * @throws Exception
	 */
	
	@PostMapping("/userList")
	public String userList(Model model, @RequestParam Map<String, Object> params) {
		JSONArray jsonArray = new JSONArray();
		try {
			OrgVo orgvo = new OrgVo();
			jsonArray = oService.orgList(orgvo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		model.addAttribute("oList", jsonArray);

		return "/user/userList";
	}

	@ResponseBody
	@PostMapping("/eachList")
	public Map<String, Object> eachList(Model model, UserVo vo,
			@RequestParam Map<String, Object> params) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<UserVo> list = new ArrayList<UserVo>();
		vo.setOrg_seq(Integer.parseInt(params.get("org_seq").toString()));


		// 페이징
		vo.setCurrentPage(vo.getListInfoCurrentPage());
		vo = (UserVo) PagingUtil.setDefaultPaging(PagingUtil.DefaultPaging, vo);
		int cnt = Integer.parseInt(userMapper.countListInfo(vo) + "");
		vo.setTotalRecordSize(cnt);
		vo = (UserVo) PagingUtil.setPaging(vo);

		list = userSerivce.userList(vo);
		dataMap.put("data", list);
		dataMap.put("paging", vo);

		return dataMap;
	}

	@PostMapping("/view/{seq}")
	public String userView(@PathVariable("seq") int seq, UserVo vo, Model model) throws Exception {
		OrgVo ovo = new OrgVo();

		UserVo uvo = userSerivce.userView(vo);
		List<OrgVo> list = userSerivce.getOrgList(ovo);

		model.addAttribute("result", uvo);
		model.addAttribute("olist", list);

		return "/user/userAdd";

	}

	@PostMapping("/userAdd")
	public String userAddView(Model model, OrgVo vo) {
		List<OrgVo> list = userSerivce.getOrgList(vo);
		model.addAttribute("olist", list);

		return "/user/userAdd";
	}

	@PostMapping("/idDuplCheck")
	@ResponseBody
	public int idDuplCheck(Model model, UserVo vo) throws Exception {
		int result = 0;
		result = userSerivce.userIdCheck(vo);
		return result;

	}

	@PostMapping("/userSave")
	public String save(Model model, UserVo vo) throws Exception {
		int result = userSerivce.userSave(vo);

		return "redirect:/user/userList";
	}


	@PostMapping("/modify")
	@ResponseBody
	public int modify(Model model, UserVo vo) throws Exception {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timefomat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		int result = 0;

		if (vo.getGubun().equals("R")) {
			vo.setDischarge_dt(timefomat.format(timestamp));
		}
		result = userSerivce.userModify(vo);
		return result;

	}

	@PostMapping("/delete")
	@ResponseBody
	public int delete(Model model, @RequestParam(value = "seqs[]") List<Integer> list)
			throws Exception {
		int result = 0;
		List<UserVo> voList = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			UserVo tmp = new UserVo();
			tmp.setSeq(list.get(i));
			voList.add(tmp);
		}

		result = userSerivce.userDelete(voList);

		return result;

	}

}
