speed = 3
dir = 1

oldDir = 0

function doBehaviour()
	
	physics = _self:getComponent(component("Physics"))
	anim 	= _self:getComponent(component("Animatable"))
	
	vecVel = physics.body:getLinearVelocity()
	
	vecVel.x = speed * dir
	vecVel.y = 0
	
	physics.body:setLinearVelocity(vecVel)
	
	if vecVel.y ~= 0 then 
		if vecVel.x < 0 then 
			anim:setCurrentAnimation("JUMPING",false,true,false) 
		elseif vecVel.x > 0 then 
			anim:setCurrentAnimation("JUMPING",false,false,false)
		end
	else 
		if vecVel.x < 0 then 
			anim:setCurrentAnimation("RUNNING",false,true,false) 
		elseif vecVel.x > 0.0 then 
			anim:setCurrentAnimation("RUNNING",false,false,false)
		else
			anim:setCurrentAnimation("IDLE")	
		end	
	end
	
end


function beginCollision(target)
	if (target == _world:getManager(manager("PlayerManager")):getPlayer()) then 	
		_self:destroy()
		b = target:getComponent(component("Physics")).body
		b:applyLinearImpulse(-18,3,b:getWorldCenter().x,b:getWorldCenter().y,true)
	else
		b = _self:getComponent(component("Physics")).body
		vec = b:getLinearVelocity()
		if (vec.x < 0) then
			vec.x = speed
		else
			vec.x = -speed
		end
		b:setLinearVelocity(vec)
	end
end

function endCollision(target)
end