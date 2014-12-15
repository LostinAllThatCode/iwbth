started = false
time = 0

_sliderDistance = 0
_sliderTimeTrigger = 0
_sliderOriginX = 0 
_sliderSpeed = 0
_sliderDir = 0


function init(t, distance, speed, dir)
	_sliderTimeTrigger = tonumber(t)
	_sliderDistance = tonumber(distance)
	_sliderSpeed = speed
	_sliderDir = dir
end

function doBehaviour()	
	time = time + _world:getDelta()
	if (time >= _sliderTimeTrigger) then started = true end
	if (started) then 
		physics = _self:getComponent(component("Physics"))
		current = physics:getBody():getPosition()
		if (math.abs(_sliderOriginX - current.x) >= _sliderDistance) then 
			_sliderDir = _sliderDir * -1 
			_sliderOriginX = current.x	
		end
		physics.body:setLinearVelocity(_sliderSpeed * _sliderDir,0)
	else
		if (origin == nil) then 
			physics = _self:getComponent(component("Physics"))
			_sliderOriginX = physics:getBody():getPosition().x
		end
	end
end


function beginCollision(target)
end

function endCollision(target)
end
