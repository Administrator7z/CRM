package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.ContactsRemark;

import java.util.List;

public interface ContactsRemarkMapper {

    void insertContactsRemarkByList(List<ContactsRemark> corList);
}