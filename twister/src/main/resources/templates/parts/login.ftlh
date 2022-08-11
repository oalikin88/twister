<#macro login path>
<div>
		<form action="${path}" method="post">
			<div>
				<label> Логин: <input type="text" name="username" />
				</label>
			</div>
			<div>
				<label> Пароль: <input type="password" name="password" />
				</label>
			</div>
			
			<div>
				<input type="submit" value="Войти" />
				<input type="hidden" name="_csrf" value="${_csrf.token}" />
			</div>
		</form>
	</div>
</#macro>

<#macro logout>
<form action="/logout" method="post">
			<input type="hidden" name="_csrf" value="${_csrf.token}" />
			<input type="submit" value="Выйти" />
		</form>
</#macro>