REQ00: 07 65 03 22 fe 70 00 00 00 00
REQ01: 07 65 03 22 fe 70 00 00 00 00
REQ02: 07 65 03 22 fe 70 00 00 00 00
REQ03: 07 65 03 22 fe 70 00 00 00 00
REQ04: 07 65 03 22 fe 70 00 00 00 00
REQ05: 07 65 03 22 fe 70 00 00 00 00
REQ06: 07 65 03 22 fe 70 00 00 00 00
REQ07: 07 65 03 22 fe 70 00 00 00 00
REQ08: 07 65 03 22 fe 70 00 00 00 00
REQ09: 07 65 03 22 fe 70 00 00 00 00
REQ10: 07 65 03 22 fe 70 00 00 00 00
REQ11: 07 65 03 22 fe 70 00 00 00 00
REQ12: 07 65 03 22 fe 70 00 00 00 00
REQ13: 07 65 03 22 fe 70 00 00 00 00
REQ14: 07 65 03 22 fe 70 00 00 00 00
REQ15: 07 65 03 22 fe 70 00 00 00 00
REQ16: 07 65 03 22 fe 70 00 00 00 00
REQ17: 07 65 03 22 fe 70 00 00 00 00
REQ18: 07 65 03 22 fe 70 00 00 00 00
REQ19: 07 65 03 22 fe 70 00 00 00 00
REQ20: 07 65 03 22 fd d5 00 00 00 00
REQ21: 07 65 03 22 fd df 00 00 00 00
REQ22: 07 65 03 22 fd df 00 00 00 00
REQ23: 07 65 03 22 fd df 00 00 00 00
REQ24: 07 65 03 22 fd df 00 00 00 00
REQ25: 07 65 03 22 f0 d2 00 00 00 00
REQ26: 07 65 03 22 f1 0d 00 00 00 00
REQ27: 07 65 03 22 f1 24 00 00 00 00
REQ28: 07 65 03 22 fe 16 00 00 00 00
REQ29: 07 65 03 22 fe 16 00 00 00 00
REQ30: 07 65 03 22 fe 16 00 00 00 00
REQ31: 07 65 03 22 fe 16 00 00 00 00
00.ACC开关                                           ANS00.byte8         (x&0x40)>>6                     0X00:未激活               其他:激活        
01.一键启动开关                                      ANS01.byte7         (x&0x80)>>7                     0X00:未激活               其他:激活        
02.中控解锁开关                                      ANS02.byte7.bit4                0X00:未激活               其他:激活        
03.中控闭锁开关                                      ANS03.byte7         (x&0x20)>>5                     0X00:未激活               其他:激活        
04.位置灯开关                                        ANS04.byte6.bit0                0X00:未激活               其他:激活        
05.倒档开关                                          ANS05.byte8         (x&0x08)>>3                     0X00:未激活               其他:激活        
06.刹车踏板开关                                      ANS06.byte8.bit4                0X00:未激活               其他:激活        
07.前洗涤开关                                        ANS07.byte5         (x&0x40)>>6                     0X00:未激活               其他:激活        
08.前雾灯开关                                        ANS08.byte7         (x&0x08)>>3                     0X00:未激活               其他:激活        
09.危险报警灯开关                                    ANS09.byte4         (x&0x80)>>7                     0X00:未激活               其他:激活        
10.右前门状态开关                                    ANS10.byte4.bit4                0X00:未激活               其他:激活        
11.右后门状态开关                                    ANS11.byte4         (x&0x04)>>2                     0X00:未激活               其他:激活        
12.右转向开关                                        ANS12.byte6         (x&0x20)>>5                     0X00:未激活               其他:激活        
13.后雾灯                                            ANS13.byte5         (x&0x02)>>1                     0X01:激活                 其他:未激活        
14.后雾灯开关                                        ANS14.byte6         (x&0x02)>>1                     0X00:未激活               其他:激活        
15.左前门状态开关                                    ANS15.byte4         (x&0x20)>>5                     0X00:未激活               其他:激活        
16.左后门状态开关                                    ANS16.byte4         (x&0x08)>>3                     0X00:未激活               其他:激活        
17.左转向开关                                        ANS17.byte6         (x&0x40)>>6                     0X00:未激活               其他:激活        
18.手刹开关                                          ANS18.byte5         (x&0x02)>>1                     0X00:未激活               其他:激活        
19.远光灯开关                                        ANS19.byte6.bit4                0X00:未激活               其他:激活        
20.当前存储的胎压传感器数                            ANS20.byte3         x                   
21.右前胎压传感器ID                                  ANS21.byte7-10                          
22.右后胎压传感器ID                                  ANS22.byte15-18                         
23.左前胎压传感器ID                                  ANS23.byte3-6                           
24.左后胎压传感器ID                                  ANS24.byte11-14                         
25.BCM配置信息(防盗控制功能)                         ANS25.byte4.bit4                0X01:使能               其他:禁用        
26.当前所处锁状态                                    ANS26.byte3         x&0xf0>>5                       0X00:解锁               0X02:中控上锁               0X04:超级锁               其他:----        
27.锁是否处于热保护                                  ANS27.byte3         (x&0x80)>>7                     0X01:是               0X00:否           
28.右转向灯                                          ANS28.byte4.bit4                0X01:激活               其他:未激活        
29.左转向灯                                          ANS29.byte4         (x&0x08)>>3                     0X01:激活               其他:未激活        
30.近光灯                                            ANS30.byte4         (x&0x04)>>2                     0X01:激活               其他:未激活        
31.远光灯                                            ANS31.byte4.bit0                0X01:激活               其他:未激活        