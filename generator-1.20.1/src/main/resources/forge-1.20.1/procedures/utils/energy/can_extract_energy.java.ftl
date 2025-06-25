private static boolean canExtractEnergy(LevelAccessor level, BlockPos pos,Direction direction) {
		AtomicBoolean _retval = new AtomicBoolean(false);
		BlockEntity _ent = level.getBlockEntity(pos);
		if (_ent != null)
			_ent.getCapability(ForgeCapabilities.ENERGY, direction).ifPresent(capability ->
				_retval.set(capability.canExtract()));
		return _retval.get();
}