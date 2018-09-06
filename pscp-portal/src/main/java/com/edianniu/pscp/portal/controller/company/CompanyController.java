package com.edianniu.pscp.portal.controller.company;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edianniu.pscp.mis.bean.company.CompanySaveOrAuthResult;
import com.edianniu.pscp.portal.entity.CompanyEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.CompanyService;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;


/**
 * ${comments}
 *
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-04 16:00:18
 */
@Controller
@RequestMapping("cp/facilitator")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping("/company.html")
    public String list() {
        return "cp/company.html";
    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info")
    @RequiresPermissions("cp:company:info")
    public R info() {
        SysUserEntity entity = ShiroUtils.getUserEntity();
        return R.ok().put("company", getCompanyEntity(entity));
    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/getCompanyInfo")
    public R noAuthInfo() {
        SysUserEntity entity = ShiroUtils.getUserEntity();
        return R.ok().put("company", getCompanyEntity(entity));
    }

    /**
     * 获取公司信息
     * @param userInfo
     * @return
     */
    private CompanyEntity getCompanyEntity(SysUserEntity userInfo) {
        CompanyEntity company = companyService.getCompanyByUid(userInfo.getUserId());
        if (company == null) {
            company = new CompanyEntity();
            company.setMemberId(userInfo.getUserId());
        }
        company.setUserName(userInfo.getMobile());
        return company;
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("cp:company:save")
    public R save(@RequestBody CompanyEntity company) {
        CompanySaveOrAuthResult result = companyService.save(company);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("cp:company:update")
    public R update(@RequestBody CompanyEntity company) {
        CompanySaveOrAuthResult result = companyService.update(company);
        if (!result.isSuccess()) {
            return R.error(result.getResultCode(), result.getResultMessage());
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("company:delete")
    public R delete(@RequestBody Long[] ids) {
        companyService.deleteBatch(ids);
        return R.ok();
    }

}
