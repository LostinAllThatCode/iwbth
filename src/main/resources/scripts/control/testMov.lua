xVelMax	= 6

function doBehaviour()
	phy = _self:getComponent(component("Physics"))
	body = phy:getBody()
	vecPos = body:getPosition()
	vecVel = body:getLinearVelocity()
	
	sign = 0
	
	if (isKeyDown(INPUT.KEY_RIGHT)) then 
		sign = 1
	elseif (isKeyDown(INPUT.KEY_LEFT)) then 
		sign = -1
	end

	if sign ~= 0 then 
		v = sign * .5
		if (vecVel.x >= xVelMax) or (vecVel.x <= -xVelMax) then v = 0 end
		body:applyLinearImpulse(v, 0, vecPos.x, vecPos.y);
	end
	
end

function beginCollision(target)

end

function endCollision(target)
end
