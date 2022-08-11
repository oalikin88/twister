<#import "parts/login.ftlh" as l>
<#import "parts/common.ftlh" as c>

<@c.page>
	<div>
		<h3>Регистрация нового пользователя</h3>
	</div>
	
${message!}

<@l.login "/registration" />
</@c.page>