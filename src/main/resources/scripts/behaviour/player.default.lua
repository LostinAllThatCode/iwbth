-- Variables needed for do
dVelY 		= 0
jumpCount	= 0

-- Static final values to determine execution 
airdrag 	= .6
maxJumps 	= 2
maxImpY		= .6
maxVelX		= 6
imp			= .06

function doBehaviour()
	physics 	= _self:getComponent(component("Physics"))
	anim		= _self:getComponent(component("Animatable"))
	
	position 	= physics:getBody():getPosition()
	
	if physics.SENSOR_FEET then 
		jumpCount = 0
		drag = 1
	else
		drag = airdrag
	end
	
	if isKeyDown(INPUT.KEY_JUMP) and jumpCount < maxJumps then 
		if dVelY == 0 then dVelY = .1 end
		dVelY = dVelY + .1
		if dVelY >= maxImpY then 
			dVelY = maxImpY
		end
	end
	
	if not isKeyDown(INPUT.KEY_JUMP) and dVelY > 0 and jumpCount < maxJumps then
		physics.body:setLinearVelocity(physics.body:getLinearVelocity().x,0)
		physics.body:applyLinearImpulse(0,dVelY,position.x,position.y,true);
		dVelY = 0
		jumpCount=jumpCount+1
		anim:setCurrentAnimation("JUMPING",true)
		physics.SENSOR_FEET = false
	end	
	
	if isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) then 
		physics.body:applyLinearImpulse(-imp,0,position.x,position.y,true);
		if physics.SENSOR_FEET then anim:setCurrentAnimation("RUNNING") end
		anim:flip(true,false)
	end
		
	if isKeyDown(INPUT.KEY_RIGHT) and not isKeyDown(INPUT.KEY_LEFT) then 
		physics.body:applyLinearImpulse(imp,0,position.x,position.y,true);
		if physics.SENSOR_FEET then anim:setCurrentAnimation("RUNNING") end
		anim:flip(false,false)
	end
	
	vecVel = physics.body:getLinearVelocity()
	
	if not isKeyDown(INPUT.KEY_LEFT) and not isKeyDown(INPUT.KEY_RIGHT) then 
		if physics.SENSOR_FEET then 
			anim:setCurrentAnimation("IDLE")
		else
			physics.body:setLinearVelocity(0,vecVel.y)
		end
		
	end
	
	if (math.abs(vecVel.x) > maxVelX or vecVel.y > 8) then 
		if (jmath:signum(vecVel.x) == 1) then 
			anim:flip(false,false)
		else
			anim:flip(true,false)
		end
		if (math.abs(vecVel.x) > maxVelX) then vecVel.x = jmath:signum(vecVel.x) * maxVelX end
		if (vecVel.y > 8) then vecVel.y = 8 end
		physics.body:setLinearVelocity(vecVel.x * drag,vecVel.y)
	end
end


function beginCollision(target)	
end

function endCollision(target)
end