package com.edianniu.pscp.portal.controller.company;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import com.edianniu.pscp.portal.entity.CompanyEntity;
import com.edianniu.pscp.portal.entity.MemberBankCardEntity;
import com.edianniu.pscp.portal.entity.SysUserEntity;
import com.edianniu.pscp.portal.service.CompanyService;
import com.edianniu.pscp.portal.service.MemberBankCardService;
import com.edianniu.pscp.portal.utils.JsonResult;
import com.edianniu.pscp.portal.utils.R;
import com.edianniu.pscp.portal.utils.ShiroUtils;


/**
 * 
 * @author yanlin.chen
 * @email yanlin.chen@edianniu.com
 * @date 2017-05-10 11:06:31
 */
@Controller
@RequestMapping("companybankcard")
public class CompanyBankCardController {
    @Autowired
    private MemberBankCardService companyBankCardService;

    @Autowired
    private CompanyService companyService;


    @RequestMapping("/companybankcard.html")
    @RequiresPermissions("companybankcard:bank")
    public String list() {
        return "cp/company_finance.html";
    }


    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info")
    @RequiresPermissions("companybankcard:info")
    public R info() {
        SysUserEntity entity = ShiroUtils.getUserEntity();
        CompanyEntity company = companyService.getCompanyByUid(entity.getUserId());
        MemberBankCardEntity companyBankCard = companyBankCardService.getAdminBankCard();
        if (company != null) {
            companyBankCard.setCompanyName(company.getName());
        }
        return R.ok().put("companyBankCard", companyBankCard);
    }

    /**
     * 保存
     */
    @ResponseBody
    @RequestMapping("/save")
    @RequiresPermissions("companybankcard:save")
    public R save(@RequestBody MemberBankCardEntity companyBankCard) {

        try {

            JsonResult json = companyBankCardService.save(companyBankCard);
            if (json.getSuccess()) {

                return R.ok().put("bean", json.getObject());
            } else {
                return R.error(json.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(500, "系统异常");
        }


    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("companybankcard:update")
    public R update(@RequestBody MemberBankCardEntity companyBankCard) {
        try {
            JsonResult json = companyBankCardService.update(companyBankCard);
            if (json.getSuccess()) {

                return R.ok().put("bean", json.getObject());
            } else {
                return R.error(json.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(500, "系统异常");
        }

    }

    /**
     * 删除
     */
    @ResponseBody
    @RequestMapping("/delete")
    @RequiresPermissions("companybankcard:delete")
    public R delete(@RequestBody Long[] ids) {
        companyBankCardService.deleteBatch(ids);

        return R.ok();
    }

}
