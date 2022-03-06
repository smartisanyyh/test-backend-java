# {
#   "id": "xxx",                  // user ID
#   "name": "test",               // user name
#   "dob": "",                    // date of birth
#   "address": "",                // user address
#   "description": "",            // user description
#   "createdAt": ""               // user created date
# }


create table if not exists biz_user
(
    id          int auto_increment,
    name        varchar(32)       null comment 'user name',
    pwd         varchar(64)       null comment 'user password',
    dob         datetime          null comment 'user date of birth',
    address     varchar(255)      null comment 'user address',
    description varchar(255)      null comment 'user description',
#   经度
    longitude   decimal(9, 6)     null comment 'user longitude',
#   纬度
    latitude    decimal(8, 6)     null comment 'user latitude',
    created_at  datetime          null comment 'user created date',
    update_at   datetime          not null comment 'update_at',
    version     int     default 1 not null comment 'version',
    is_delete   boolean default 0 not null comment 'is_delete',
    constraint biz_user_pk
        primary key
            (
             id
                )
)
    comment 'user';
