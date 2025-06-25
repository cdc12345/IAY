public static int getMaxEnergyStored(LevelAccessor level, BlockPos pos, Direction direction) {
	AtomicInteger _retval = new AtomicInteger(0);
	BlockEntity _ent = level.getBlockEntity(pos);
	if (_ent != null)
		_ent.getCapability(ForgeCapabilities.ENERGY, direction).ifPresent(capability ->
			_retval.set(capability.getMaxEnergyStored()));
	return _retval.get();
}