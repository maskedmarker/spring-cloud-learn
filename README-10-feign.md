# 关于feign

## todo
1.为什么@FeignClient所在的类不需要添加@Component?
FeignClientsRegistrar implements ImportBeanDefinitionRegistrar,会将@FeignClient所在的类注册到context中
