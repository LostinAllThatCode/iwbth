moveX = 0
time = 0
starttime = 0
started = false

originX = 0 

speed = 5
dir = 1

function init(t, distance)
	starttime = t
	moveX = distance
end

function doBehaviour()	
	time = time + _world:getDelta()
	if (time >= starttime) then started = true end
	if (started) then 
		physics = _self:getComponent(component("Physics"))
		current = physics:getBody():getPosition()
		if (math.abs(originX - current.x) >= moveX) then 
			dir = dir * -1 
			originX = current.x	
		end
		physics.body:setLinearVelocity(speed * dir,0)
	else
		if (origin == nil) then 
			physics = _self:getComponent(component("Physics"))
			originX = physics:getBody():getPosition().x
		end
	end
end


function beginCollision(target)
end

function endCollision(target)
end
