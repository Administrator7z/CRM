package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationMapper {

    void insertContactsActivityRelationByList(List<ContactsActivityRelation> coarList);
}