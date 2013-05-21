/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2011/10/30 11:34:02                          */
/*==============================================================*/


drop view V_AUTHORITY_SECUREOBJ;

drop view V_USER_AUTHORITY;

drop table T_GROUP cascade constraints;

drop table T_GROUP_ROLE cascade constraints;

drop table T_ROLE cascade constraints;

drop table T_ROLE_SECUREOBJ cascade constraints;

drop table T_SECURE_OBJ cascade constraints;

drop table T_SECURE_OBJ_GROUP cascade constraints;

drop table T_USER cascade constraints;

drop table T_USER_GROUP cascade constraints;

drop table T_USER_ROLE cascade constraints;

drop sequence ID_GROUP;

drop sequence ID_SECURE_OBJ;

drop sequence ID_SECURE_OBJ_GROUP;

drop sequence ID_USER_GROUP;

drop sequence ID_USER_ROLE;

create sequence ID_GROUP;

create sequence ID_SECURE_OBJ;

create sequence ID_SECURE_OBJ_GROUP;

create sequence ID_USER_GROUP;

create sequence ID_USER_ROLE;

/*==============================================================*/
/* Table: T_GROUP                                               */
/*==============================================================*/
create table T_GROUP  (
   ID_GROUP             INTEGER                         not null,
   GROUP_NAME           VARCHAR2(100),
   STATUS               CHAR(1)                        default '1'
      constraint CKC_STATUS_T_GROUP check (STATUS is null or (STATUS in ('0','1'))),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   MEMO                 VARCHAR(1000),
   constraint PK_T_GROUP primary key (ID_GROUP)
);

/*==============================================================*/
/* Table: T_GROUP_ROLE                                          */
/*==============================================================*/
create table T_GROUP_ROLE  (
   ID_GROUP             INTEGER,
   ROLE_ID              VARCHAR2(20)
);

/*==============================================================*/
/* Table: T_ROLE                                                */
/*==============================================================*/
create table T_ROLE  (
   ROLE_ID              VARCHAR2(20)                    not null,
   ROLE_NAME            VARCHAR2(100),
   STATUS               CHAR(1)                        default '1'
      constraint CKC_STATUS_T_ROLE check (STATUS is null or (STATUS in ('0','1'))),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   MEMO                 VARCHAR2(1000),
   constraint PK_T_ROLE primary key (ROLE_ID)
);

/*==============================================================*/
/* Table: T_ROLE_SECUREOBJ                                      */
/*==============================================================*/
create table T_ROLE_SECUREOBJ  (
   ID_ROLE_SECUREOBJ    INTEGER                         not null,
   ROLE_ID              VARCHAR2(20),
   ID_SECURE_OBJ        INTEGER,
   constraint PK_T_ROLE_SECUREOBJ primary key (ID_ROLE_SECUREOBJ)
);

/*==============================================================*/
/* Table: T_SECURE_OBJ                                          */
/*==============================================================*/
create table T_SECURE_OBJ  (
   ID_SECURE_OBJ        INTEGER                         not null,
   ID_SECURE_OBJ_GROUP  INTEGER,
   OBJ_NAME             VARCHAR2(100),
   SORT_IDX             INTEGER                        default 0 not null,
   TYPE                 CHAR(2),
   OBJ_RESOURCE         VARCHAR2(400),
   STATUS               CHAR(1)                        default '1'
      constraint CKC_STATUS_T_SECURE check (STATUS is null or (STATUS in ('0','1'))),
   MEMO                 VARCHAR2(1000),
   constraint PK_T_SECURE_OBJ primary key (ID_SECURE_OBJ)
);

/*==============================================================*/
/* Table: T_SECURE_OBJ_GROUP                                    */
/*==============================================================*/
create table T_SECURE_OBJ_GROUP  (
   ID_SECURE_OBJ_GROUP  INTEGER                         not null,
   SO_GROUP_NAME        VARCHAR2(200),
   SORT_IDX             INTEGER                        default 0 not null,
   MEMO                 VARCHAR2(1000),
   constraint PK_T_SECURE_OBJ_GROUP primary key (ID_SECURE_OBJ_GROUP)
);

/*==============================================================*/
/* Table: T_USER                                                */
/*==============================================================*/
create table T_USER  (
   USER_ID              VARCHAR2(50)                    not null,
   USER_NAME            VARCHAR2(100),
   PASSWORD             VARCHAR2(512),
   STATUS               CHAR(1)                        default '1'
      constraint CKC_STATUS_T_USER check (STATUS is null or (STATUS in ('0','1','2'))),
   CREATE_TIME          TIMESTAMP,
   UPDATE_TIME          TIMESTAMP,
   constraint PK_T_USER primary key (USER_ID)
);

/*==============================================================*/
/* Table: T_USER_GROUP                                          */
/*==============================================================*/
create table T_USER_GROUP  (
   USER_ID              VARCHAR2(50),
   ID_GROUP             INTEGER
);

/*==============================================================*/
/* Table: T_USER_ROLE                                           */
/*==============================================================*/
create table T_USER_ROLE  (
   ROLE_ID              VARCHAR2(20),
   USER_ID              VARCHAR2(50)
);

/*==============================================================*/
/* View: V_AUTHORITY_SECUREOBJ                                  */
/*==============================================================*/
create or replace view V_AUTHORITY_SECUREOBJ as
select ROLE_ID, ROLE_NAME, ID_SECURE_OBJ_GROUP, SO_GROUP_NAME, SOG_SORT_IDX, ID_SECURE_OBJ, OBJ_NAME, SO_SORT_IDX, OBJ_TYPE, OBJ_RESOURCE from (select r.ROLE_ID, r.ROLE_NAME, so.ID_SECURE_OBJ_GROUP, sog.SO_GROUP_NAME, sog.SORT_IDX as SOG_SORT_IDX, so.ID_SECURE_OBJ, so.OBJ_NAME, so.SORT_IDX as SO_SORT_IDX, so.TYPE as OBJ_TYPE, so.OBJ_RESOURCE
  from t_role r, t_role_secureobj rs, t_secure_obj so, t_secure_obj_group sog
 where r.ROLE_ID = rs.ROLE_ID and rs.ID_SECURE_OBJ = so.ID_SECURE_OBJ and so.ID_SECURE_OBJ_GROUP = sog.ID_SECURE_OBJ_GROUP and
       r.STATUS = '1' and so.STATUS = '1' order by sog.SORT_IDX, so.SORT_IDX)
with read only;

/*==============================================================*/
/* View: V_USER_AUTHORITY                                       */
/*==============================================================*/
create or replace view V_USER_AUTHORITY as
select u.USER_ID, u.USER_NAME, u.PASSWORD, r.ROLE_ID, r.ROLE_NAME
  from t_user u, t_group g, t_user_group ug, t_group_role gr, t_role r
where u.USER_ID = ug.USER_ID and ug.ID_GROUP = g.ID_GROUP and gr.ID_GROUP = g.ID_GROUP and gr.ROLE_ID = r.ROLE_ID and
      u.STATUS = '1' and g.STATUS = '1' and r.STATUS = '1'
union
select u.USER_ID, u.USER_NAME, u.PASSWORD, r.ROLE_ID, r.ROLE_NAME
  from t_user u, t_user_role ur, t_role r
 where u.USER_ID = ur.USER_ID and ur.ROLE_ID = r.ROLE_ID and
       r.STATUS = '1'
with read only;

